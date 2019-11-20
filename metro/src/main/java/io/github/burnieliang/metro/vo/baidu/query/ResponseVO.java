package io.github.burnieliang.metro.vo.baidu.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseVO {

    private int status;

    private String message;

    private int total;

    private List<Result> results;

}
