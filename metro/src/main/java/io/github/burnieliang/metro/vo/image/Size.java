package io.github.burnieliang.metro.vo.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Boning Liang
 * @date 2019-11-22 10:53:06
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Size {

    private Integer height;

    private Integer width;

}
