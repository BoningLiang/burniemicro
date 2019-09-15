package edu.auburn.bzl0048.metro.controller;

import edu.auburn.bzl0048.metro.service.MetroService;
import edu.auburn.bzl0048.metro.vo.responseVOs.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangboning
 * @date 2019/7/22 17:25
 */
@RestController
@Slf4j
@RequestMapping("/echarts")
public class EChartsController {

    @Autowired
    private MetroService metroService;

    @GetMapping("/api/volume/{citycode}")
    public BaseResponseVO volume(@PathVariable String citycode) {
        return metroService.getEchartsVolumeByCityCode(citycode);
    }

}
