package io.github.burnieliang.metro.vo.responseVOs;

import lombok.Data;

import java.util.List;

/**
 * @author liangboning
 * @date 2019/7/22 17:18
 */
@Data
public class EChartsOptionVO<T> {
    private String type;
    private List<T> data;
}
