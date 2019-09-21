package io.github.burnieliang.metro.service;

import io.github.burnieliang.metro.entity.Volume;

import java.util.List;

/**
 * @author liangboning
 * @date 2019/7/17 12:05
 */
public interface VolumeService {

    List<Volume> getAllVolumesByCityId(Long cityId);

    boolean insertVolumes(List<Volume> volumes);

}
