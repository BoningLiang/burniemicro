package io.github.burnieliang.metro.vo.baidu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Boning Liang
 * @date 2019-11-22 10:54:34
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CoordinateRange {

    private Double maxLat;

    private Double minLat;

    private Double maxLon;

    private Double minLon;

}
