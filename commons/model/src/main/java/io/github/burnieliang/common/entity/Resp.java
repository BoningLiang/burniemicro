package io.github.burnieliang.common.entity;


import io.github.burnieliang.common.constant.CommonConstants;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class Resp<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code = CommonConstants.SUCCESS;

    @Getter
    @Setter
    private String msg = "success";

    @Getter
    @Setter
    private T data;

    public Resp() {
        super();
    }

    public Resp(T data) {
        this.data = data;
    }

    public Resp(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public Resp(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public Resp(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = CommonConstants.FAIL;
    }
}
