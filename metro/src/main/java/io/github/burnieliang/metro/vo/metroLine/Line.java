package io.github.burnieliang.metro.vo.metroLine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Line {

    private String name;

    private String city;

    private List<Station> stations;

    private boolean isExchange;

}
