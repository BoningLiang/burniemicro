package io.github.burnieliang.metro.vo.responseVOs;

import io.github.burnieliang.metro.entity.City;
import lombok.Data;

import java.util.List;

/**
 * @author liangboning
 * @date 2019/7/22 16:47
 */
@Data
public class ChartDataVO {

    private City city;

    private List<VolumeVO> volumeVOs;

}
