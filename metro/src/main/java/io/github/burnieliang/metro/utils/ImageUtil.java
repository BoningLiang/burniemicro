package io.github.burnieliang.metro.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {

    public BufferedImage createImage() {
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        graphics2D.drawLine(30, 30, 60, 60);

        return bufferedImage;
    }

}
