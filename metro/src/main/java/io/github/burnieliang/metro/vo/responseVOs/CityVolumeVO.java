package io.github.burnieliang.metro.vo.responseVOs;

import io.github.burnieliang.metro.entity.City;
import io.github.burnieliang.metro.entity.Volume;
import lombok.Data;

import java.util.List;

/**
 * @author liangboning
 * @date 2019/7/17 12:13
 */
@Data
public class CityVolumeVO {

    private City city;

    private List<Volume> volumes;
}
