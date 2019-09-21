package io.github.burnieliang.metro.controller;

import io.github.burnieliang.common.entity.Resp;
import io.github.burnieliang.metro.service.MetroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "echarts", tags = "ECharts模块")
public class EChartsController {

    @Autowired
    private MetroService metroService;

    @GetMapping("/api/volume/{citycode}")
    @ApiOperation(value = "人次")
    public Resp volume(@PathVariable String citycode) throws Exception {
        return new Resp<>(metroService.getEchartsVolumeByCityCode(citycode));
    }

}
