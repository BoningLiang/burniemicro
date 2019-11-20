package io.github.burnieliang.metro.vo.baidu.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.burnieliang.metro.vo.baidu.Location;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Result {

    private String name;

    private Location location;

    private String address;

    private String province;

    private String city;

    private String area;

    private int detail;

    private String uid;

    private DetailInfo detailInfo;

}
