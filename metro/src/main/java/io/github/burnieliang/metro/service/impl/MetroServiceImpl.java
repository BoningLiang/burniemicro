package io.github.burnieliang.metro.service.impl;

import io.github.burnieliang.metro.entity.City;
import io.github.burnieliang.metro.entity.Volume;
import io.github.burnieliang.metro.exception.FileFormatException;
import io.github.burnieliang.metro.service.CityService;
import io.github.burnieliang.metro.service.MetroService;
import io.github.burnieliang.metro.service.VolumeService;
import io.github.burnieliang.metro.utils.ExtractUtil;
import io.github.burnieliang.metro.vo.requestVOs.AddVolumeVO;
import io.github.burnieliang.metro.vo.responseVOs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by liangboning on 2019/7/12.
 */
@Service
public class MetroServiceImpl implements MetroService {

    @Autowired
    private CityService cityService;

    @Autowired
    private VolumeService volumeService;

    @Override
    public CityVolumeVO getAllVolumesByCityCode(String citycode) throws Exception {
        City city = cityService.getCityByCityCode(citycode);
        if (city == null) {
            throw new Exception("No such city");
        }else {
            List<Volume> volumes = volumeService.getAllVolumesByCityId(city.getId());
            CityVolumeVO cityVolumeVO = new CityVolumeVO();
            cityVolumeVO.setCity(city);
            cityVolumeVO.setVolumes(volumes);
            if (volumes == null || volumes.size()==0) {
                throw new Exception("No volume data");
            }else {
                return cityVolumeVO;
            }
        }
    }

    @Override
    public EChartsDataVO getEchartsVolumeByCityCode(String citycode) throws Exception {
        City city = cityService.getCityByCityCode(citycode);
        if (city == null) {
            throw new Exception("No such city");
        }else {
            List<Volume> volumes = volumeService.getAllVolumesByCityId(city.getId());
            EChartsDataVO eChartsDataVO = new EChartsDataVO();
            EChartsOptionVO<String> xAxis = new EChartsOptionVO<>();
            EChartsOptionVO<String> yAxis = new EChartsOptionVO<>();
            EChartsOptionVO<Long> series = new EChartsOptionVO<>();
            List<EChartsOptionVO<Long>> seriesList = new LinkedList<>();
            xAxis.setType("category");
            yAxis.setType("value");
            series.setType("line");
            List<String> dateList = new LinkedList<>();
            List<Long> dataList = new LinkedList<>();
            volumes.forEach(volume -> {
                dateList.add(volume.getDate().toString());
                dataList.add(volume.getVolume());
            });
            xAxis.setData(dateList);
            series.setData(dataList);
            seriesList.add(series);

            eChartsDataVO.setXAxis(xAxis);
            eChartsDataVO.setYAxis(yAxis);
            eChartsDataVO.setSeries(seriesList);

            if (volumes == null || volumes.size()==0) {
                throw new Exception("No volume data");
            }else {
                return eChartsDataVO;
            }
        }
    }

    @Override
    public boolean addVolumes(AddVolumeVO addVolumeVO) {
        return volumeService.insertVolumes(addVolumeVO.getVolumes());
    }

    @Override
    public boolean addVolumesFromFile(MultipartFile file) throws FileFormatException {
        AddVolumeVO addVolumeVO = ExtractUtil.extractContent(file, cityService);
        return volumeService.insertVolumes(addVolumeVO.getVolumes());
    }

    @Override
    public ChartDataVO getChartDataByCityCode(String citycode) throws Exception {
        City city = cityService.getCityByCityCode(citycode);
        if (city == null) {
            throw new Exception("No such city");
        }else {
            List<Volume> volumes = volumeService.getAllVolumesByCityId(city.getId());
            List<VolumeVO> volumeVOs = ExtractUtil.convertVolumeToVolumeVO(volumes);
            ChartDataVO chartDataVO = new ChartDataVO();
            chartDataVO.setCity(city);
            chartDataVO.setVolumeVOs(volumeVOs);
            if (volumes == null || volumes.size()==0) {
                throw new Exception("No volume data");
            }else {
                return chartDataVO;
            }
        }
    }

    @Override
    public City getCityByCityName(String cityName) {
        City city = cityService.getCityByCityName(cityName);
        return city;
    }
}
