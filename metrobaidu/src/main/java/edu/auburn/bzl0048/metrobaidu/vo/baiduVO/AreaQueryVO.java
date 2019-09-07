package edu.auburn.bzl0048.metrobaidu.vo.baiduVO;

import lombok.Data;

@Data
public class AreaQueryVO {

    private String query;

    private String tag;

    private String region;

    private String city_limit;

    private String output;

    private String scope;

    private String filter;

    private String coord_type;

    private String ret_coordtype;

    private String page_size;

    private String page_num;

    private String ak;

    private String sn;

    private String timestamp;

}
