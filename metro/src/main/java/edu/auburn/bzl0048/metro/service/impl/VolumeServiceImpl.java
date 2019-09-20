package edu.auburn.bzl0048.metro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.auburn.bzl0048.metro.entity.Volume;
import edu.auburn.bzl0048.metro.mapper.VolumeMapper;
import edu.auburn.bzl0048.metro.service.VolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liangboning
 * @date 2019/7/17 12:06
 */
@Service("volumeService")
public class VolumeServiceImpl implements VolumeService {

    @Autowired
    private VolumeMapper volumeMapper;

    @Override
    public List<Volume> getAllVolumesByCityId(Long cityId) {
        QueryWrapper<Volume> volumeQueryWrapper = new QueryWrapper<>();
        volumeQueryWrapper.eq("cityId", cityId);
        volumeQueryWrapper.orderByAsc("date");
        return volumeMapper.selectList(volumeQueryWrapper);
    }

    @Override
    public void insertVolumes(List<Volume> volumes) {
        volumes.forEach(volumeMapper::insert);
    }
}
