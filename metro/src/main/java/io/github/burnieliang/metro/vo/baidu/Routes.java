package io.github.burnieliang.metro.vo.baidu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Routes {

    private Location origin;
    private Location destination;
    private String tag;
    private int distance;
    private int duration;
    private int taxiFee;
    private int toll;
    private int tollDistance;
    private List<Steps> steps;

}
