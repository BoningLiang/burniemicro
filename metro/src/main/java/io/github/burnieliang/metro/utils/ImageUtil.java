package io.github.burnieliang.metro.utils;

import io.github.burnieliang.metro.vo.baidu.CoordinateRange;
import io.github.burnieliang.metro.vo.baidu.Location;
import io.github.burnieliang.metro.vo.image.ImageXY;
import io.github.burnieliang.metro.vo.image.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {

    private double times;
    private CoordinateRange range;
    double latDiff;
    double lonDiff;
    int height;
    int width;

    public BufferedImage createImage() {
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        graphics2D.drawLine(30, 30, 60, 60);

        return bufferedImage;
    }

    public BufferedImage create(Location location1, Location location2) {
        CoordinateRange range = new CoordinateRange();
        range.setMinLat(19.3749340334);
        range.setMaxLat(47.9878106274);
        range.setMinLon(83.8007320375);
        range.setMaxLon(134.2014348650);

        ImageUtil imageUtil = new ImageUtil();
        Size size = imageUtil.getSize(range, 1000);

        BufferedImage bufferedImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        ImageXY imageXY1 = imageUtil.getXY(location1);
        ImageXY imageXY2 = imageUtil.getXY(location2);

        graphics2D.drawLine(imageXY1.getX(), imageXY1.getY(), imageXY2.getX(), imageXY2.getY());

        return bufferedImage;
    }


    public Size getSize(CoordinateRange range, Integer height) {
        this.range = range;

        this.latDiff = range.getMaxLat() - range.getMinLat();
        this.lonDiff = range.getMaxLon() - range.getMinLon();

        Double times = lonDiff / latDiff;

        Size size = new Size();
        size.setHeight(height);
        double widthDouble = height * times;
        size.setWidth((int) widthDouble);
        this.width = (int) widthDouble;
        this.height = height;
        return size;
    }

    public ImageXY getXY(Location location) {
        double x = (location.getLat() - range.getMinLat()) / latDiff;
        double y = (location.getLng() - range.getMinLon()) / lonDiff;
        ImageXY imageXY = new ImageXY();
        imageXY.setX((int) (x * height));
        imageXY.setY((int) (y * width));
        return imageXY;
    }


    public static void main(String[] args) {
        CoordinateRange range = new CoordinateRange();
        range.setMinLat(19.3749340334);
        range.setMaxLat(47.9878106274);
        range.setMinLon(83.8007320375);
        range.setMaxLon(134.2014348650);

        ImageUtil imageUtil = new ImageUtil();
        Size size = imageUtil.getSize(range, 100);
        System.out.println(size);
    }

}
