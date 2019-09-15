package edu.auburn.bzl0048.metro.service;

import edu.auburn.bzl0048.metro.exception.FileFormatException;
import edu.auburn.bzl0048.metro.vo.requestVOs.AddVolumeVO;
import edu.auburn.bzl0048.metro.vo.responseVOs.BaseResponseVO;
import edu.auburn.bzl0048.metro.vo.responseVOs.CityVolumeVO;
import edu.auburn.bzl0048.metro.vo.responseVOs.EChartsDataVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by liangboning on 2019/7/12.
 */
public interface MetroService {

    BaseResponseVO<CityVolumeVO> getAllVolumesByCityCode(String citycode);

    BaseResponseVO<EChartsDataVO> getEchartsVolumeByCityCode(String citycode);

    BaseResponseVO addVolumes(AddVolumeVO addVolumeVO);

    BaseResponseVO addVolumesFromFile(MultipartFile file) throws FileFormatException;

    BaseResponseVO getChartDataByCityCode(String citycode);

    BaseResponseVO getCityByCityName(String cityName);

}