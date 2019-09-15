package edu.auburn.bzl0048.metro.vo.responseVOs;

import edu.auburn.bzl0048.metro.entity.City;
import edu.auburn.bzl0048.metro.entity.Volume;
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
