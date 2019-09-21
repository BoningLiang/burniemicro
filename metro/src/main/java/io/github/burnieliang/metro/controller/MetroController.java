package io.github.burnieliang.metro.controller;

import io.github.burnieliang.common.entity.Resp;
import io.github.burnieliang.metro.exception.FileFormatException;
import io.github.burnieliang.metro.service.MetroService;
import io.github.burnieliang.metro.vo.requestVOs.AddVolumeVO;
import io.github.burnieliang.metro.vo.requestVOs.RequestCityVO;
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
}
