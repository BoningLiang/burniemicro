package io.github.burnieliang.metro.utils;

import io.github.burnieliang.metro.entity.City;
import io.github.burnieliang.metro.entity.Volume;
import io.github.burnieliang.metro.exception.FileFormatException;
import io.github.burnieliang.metro.service.CityService;
import io.github.burnieliang.metro.vo.requestVOs.AddVolumeVO;
import io.github.burnieliang.metro.vo.responseVOs.VolumeVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangboning
 * @date 2019/7/17 15:39
 */
public class ExtractUtil {

    public static AddVolumeVO extractContent(MultipartFile file, CityService cityService) throws FileFormatException {
        String filename = file.getOriginalFilename();
        if (filename.endsWith(".csv")) {
            String cityCode = filename.replaceAll(".csv", "");
            City city = cityService.getCityByCityCode(cityCode);
            if (city == null){
                throw new FileFormatException("文件格式错误，未找到"+cityCode);
            }else {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                    String line;
                    AddVolumeVO addVolumeVO = new AddVolumeVO();
                    List<Volume> volumes = new ArrayList<>();
                    addVolumeVO.setCity(city);
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] splited_line = line.split(",");
                        if (splited_line.length != 3) {
                            throw new FileFormatException("文件格式错误");
                        }else {
                            String dateString = splited_line[0];
                            String volumeString = splited_line[1];
                            String metersString = splited_line[2];
                            LocalDate localDate = LocalDate.parse(dateString);
                            Long volumeLong = Long.parseLong(volumeString);
                            Long metersLong = Long.parseLong(metersString);
                            Volume volume = new Volume();
                            volume.setCityid(city.getId());
                            volume.setMeters(metersLong);
                            volume.setDate(localDate);
                            volume.setVolume(volumeLong);
                            volumes.add(volume);
                        }
                    }
                    addVolumeVO.setVolumes(volumes);
                    return addVolumeVO;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }else {
            return null;
        }
    }

    public static List<VolumeVO> convertVolumeToVolumeVO(List<Volume> volumes) {
        Long sum = 0L;

        List<VolumeVO> volumeVOList = new ArrayList<>();
        int count = 1;
        for (Volume volume: volumes) {
            sum += volume.getVolume();
            VolumeVO volumeVO = new VolumeVO();

            volumeVO.setId(volume.getId());
            volumeVO.setDate(volume.getDate());
            volumeVO.setMeters(volume.getMeters());
            volumeVO.setVolume(volume.getVolume());
            volumeVO.setAverage((double)sum/(double) count);

            volumeVOList.add(volumeVO);
            count++;
        }
        return volumeVOList;
    }

}
