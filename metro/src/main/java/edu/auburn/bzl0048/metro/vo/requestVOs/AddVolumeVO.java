package edu.auburn.bzl0048.metro.vo.requestVOs;

import edu.auburn.bzl0048.metro.entity.City;
import edu.auburn.bzl0048.metro.entity.Volume;
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
