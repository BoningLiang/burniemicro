package edu.auburn.bzl0048.metro.vo.responseVOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liangboning
 * @date 2019/7/22 17:17
 */
@Data
public class EChartsDataVO {
    @JsonProperty("xAxis")
    private EChartsOptionVO<String> xAxis;
    @JsonProperty("yAxis")
    private EChartsOptionVO yAxis;
    private List<EChartsOptionVO<Long>> series;
}
