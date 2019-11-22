package io.github.burnieliang.metro.controller;

import io.github.burnieliang.common.entity.Resp;
import io.github.burnieliang.metro.exception.FileFormatException;
import io.github.burnieliang.metro.service.BaiduService;
import io.github.burnieliang.metro.service.MetroService;
import io.github.burnieliang.metro.utils.ImageUtil;
import io.github.burnieliang.metro.utils.MetroUtil;
import io.github.burnieliang.metro.vo.baidu.Location;
import io.github.burnieliang.metro.vo.baidu.query.ResponseVO;
import io.github.burnieliang.metro.vo.metroLine.Line;
import io.github.burnieliang.metro.vo.requestVOs.AddVolumeVO;
import io.github.burnieliang.metro.vo.requestVOs.RequestCityVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangboning on 2019/7/9.
 *
 */
@RestController
@Slf4j
@RequestMapping("/metro")
public class MetroController {

    @Autowired
    private MetroService metroService;

    @Autowired
    private BaiduService baiduService;

    private static final String JPEG = "jpeg";

    @GetMapping("/volume/{citycode}")
    public Resp volume(@PathVariable String citycode) throws Exception {
        return new Resp<>(metroService.getAllVolumesByCityCode(citycode));
    }

    @PostMapping("/addFromFile")
    public Resp addFromFile(@RequestParam("file") MultipartFile file) throws FileFormatException {
        return new Resp<>(metroService.addVolumesFromFile(file));
    }

    @PostMapping("/add")
    public Resp add(AddVolumeVO addVolumeVO) {
        return new Resp<>(metroService.addVolumes(addVolumeVO));
    }

    @GetMapping("/data/{citycode}")
    public Resp data(@PathVariable String citycode) throws Exception {
        return new Resp<>(metroService.getChartDataByCityCode(citycode));
    }

    @PostMapping("/city")
    public Resp city(RequestCityVO requestCityVO) {
        return new Resp<>(metroService.getCityByCityName(requestCityVO.getCityName()));
    }

    @GetMapping("/image")
    @SneakyThrows
    public void image(HttpServletResponse response) {
        ImageUtil imageUtil = new ImageUtil();
//        BufferedImage bufferedImage = imageUtil.createImage();
        Location location1 = new Location();
        Location location2 = new Location();
        //25.0886787571,98.6569377825 云南
        //40.3046716172,116.6746654443 北京
        location1.setLat(25.0886787571);
        location1.setLng(98.6569377825);
        location2.setLat(40.3046716172);
        location2.setLng(116.6746654443);
        BufferedImage bufferedImage = imageUtil.create(location1, location2);
        // 转换流信息写出
        response.setContentType("image/jpeg");
        response.setStatus(200);
        ImageIO.write(bufferedImage, JPEG, response.getOutputStream());
    }

    @GetMapping("/image2")
    public void image2(HttpServletResponse response) throws Exception{
        ImageUtil imageUtil = new ImageUtil();
        BufferedImage bufferedImage = imageUtil.createImage();
        // 转换流信息写出
        response.setContentType("image/jpeg");
        response.setStatus(200);
        ImageIO.write(bufferedImage, JPEG, response.getOutputStream());
    }

    @GetMapping("/query")
    @SneakyThrows
    public Resp query() {
        ResponseVO responseVO = baiduService.getAll("石家庄");
        MetroUtil metroUtil = new MetroUtil();
        List<Line> lines = metroUtil.parseLines(responseVO);
        metroUtil.sort(lines);
        return new Resp<>(lines);
    }

    @GetMapping("/metroimage")
    public void metroimage(HttpServletResponse response) throws Exception{
        ImageUtil imageUtil = new ImageUtil();
        BufferedImage bufferedImage = imageUtil.createImage();
        // 转换流信息写出
        response.setContentType("image/jpeg");
        response.setStatus(200);
        ImageIO.write(bufferedImage, JPEG, response.getOutputStream());
    }
}
