package edu.auburn.bzl0048.metro.vo.responseVOs;

import edu.auburn.bzl0048.metro.entity.City;
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
