package io.github.burnieliang.metro.vo.baidu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DrivingRequestVO {

    private Location origin;

    private Location destination;

    private String ak;

    private String coordType;

}
