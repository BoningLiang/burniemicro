package io.github.burnieliang.metro.vo.baidu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CoordinateBean {

    /**
     * 定位的几个基本信息
     */
    private double longitude;

    private double latitude;

    private boolean isChina;

    public CoordinateBean() {

    }

    public CoordinateBean(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CoordinateBean(String coord) {
        String[] c = coord.split(",");
        this.longitude = Double.valueOf(c[0]);
        this.latitude = Double.valueOf(c[1]);
    }

    @Override
    public String toString() {
        return this.longitude + "," + this.latitude;
    }
}
