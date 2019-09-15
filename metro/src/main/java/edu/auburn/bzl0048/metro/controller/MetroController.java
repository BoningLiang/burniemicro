package edu.auburn.bzl0048.metro.controller;

import edu.auburn.bzl0048.metro.exception.FileFormatException;
import edu.auburn.bzl0048.metro.service.MetroService;
import edu.auburn.bzl0048.metro.vo.requestVOs.AddVolumeVO;
import edu.auburn.bzl0048.metro.vo.requestVOs.RequestCityVO;
import edu.auburn.bzl0048.metro.vo.responseVOs.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/volume/{citycode}")
    public BaseResponseVO volume(@PathVariable String citycode) {
        return metroService.getAllVolumesByCityCode(citycode);
    }

    @PostMapping("/addFromFile")
    public BaseResponseVO addFromFile(@RequestParam("file") MultipartFile file) throws FileFormatException {
        return metroService.addVolumesFromFile(file);
    }

    @PostMapping("/add")
    public BaseResponseVO add(AddVolumeVO addVolumeVO) {
        return metroService.addVolumes(addVolumeVO);
    }

    @GetMapping("/data/{citycode}")
    public BaseResponseVO data(@PathVariable String citycode) {
        return metroService.getChartDataByCityCode(citycode);
    }

    @PostMapping("/city")
    public BaseResponseVO city(RequestCityVO requestCityVO) {
        return metroService.getCityByCityName(requestCityVO.getCityName());
    }
}
