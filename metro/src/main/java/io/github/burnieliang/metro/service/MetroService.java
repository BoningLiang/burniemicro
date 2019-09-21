package io.github.burnieliang.metro.service;

import io.github.burnieliang.metro.entity.City;
import io.github.burnieliang.metro.exception.FileFormatException;
import io.github.burnieliang.metro.vo.requestVOs.AddVolumeVO;
import io.github.burnieliang.metro.vo.responseVOs.ChartDataVO;
import io.github.burnieliang.metro.vo.responseVOs.CityVolumeVO;
import io.github.burnieliang.metro.vo.responseVOs.EChartsDataVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by liangboning on 2019/7/12.
 */
public interface MetroService {

    CityVolumeVO getAllVolumesByCityCode(String citycode) throws Exception;

    EChartsDataVO getEchartsVolumeByCityCode(String citycode) throws Exception;

    boolean addVolumes(AddVolumeVO addVolumeVO);

    boolean addVolumesFromFile(MultipartFile file) throws FileFormatException;

    ChartDataVO getChartDataByCityCode(String citycode) throws Exception;

    City getCityByCityName(String cityName);

}
