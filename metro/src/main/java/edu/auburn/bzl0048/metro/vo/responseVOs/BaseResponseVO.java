package edu.auburn.bzl0048.metro.vo.responseVOs;

import lombok.Data;

/**
 * @author liangboning
 * @date 2019/7/17 12:14
 */
@Data
public class BaseResponseVO<T> {

    private String resultcode;

    private String message;

    private String responseType;

    private T data;

}
