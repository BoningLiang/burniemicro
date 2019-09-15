package edu.auburn.bzl0048.metro.service;

import edu.auburn.bzl0048.metro.entity.Volume;

import java.util.List;

/**
 * @author liangboning
 * @date 2019/7/17 12:05
 */
public interface VolumeService {

    List<Volume> getAllVolumesByCityId(Long cityId);

    void insertVolumes(List<Volume> volumes);

}
