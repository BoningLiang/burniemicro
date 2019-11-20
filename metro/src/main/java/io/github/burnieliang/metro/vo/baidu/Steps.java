package io.github.burnieliang.metro.vo.baidu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Steps {

    private int legIndex;
    private String roadName;
    private int direction;
    private int distance;
    private int roadType;
    private int toll;
    private int tollDistance;
    private List<TrafficCondition> trafficCondition;
    private String path;
    private Location startLocation;
    private Location endLocation;

}
