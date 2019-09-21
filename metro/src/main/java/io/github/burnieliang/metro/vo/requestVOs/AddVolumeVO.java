package io.github.burnieliang.metro.vo.requestVOs;

import io.github.burnieliang.metro.entity.City;
import io.github.burnieliang.metro.entity.Volume;
import lombok.Data;

import java.util.List;

/**
 * @author liangboning
 * @date 2019/7/17 15:31
 */
@Data
public class AddVolumeVO {
    private City city;
    private List<Volume> volumes;
}
