package edu.auburn.bzl0048.metro.vo.responseVOs;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author liangboning
 * @date 2019/7/22 16:39
 */
@Data
public class VolumeVO {

    private String id;

    private LocalDate date;

    private Long volume;

    private Long meters;

    private Double average;

}
