package io.github.burnieliang.metro.service;

import io.github.burnieliang.metro.vo.baidu.DrivingOutput;
import io.github.burnieliang.metro.vo.baidu.DrivingRequestVO;
import io.github.burnieliang.metro.vo.baidu.query.ResponseVO;

import java.io.IOException;

public interface BaiduService {

    DrivingOutput get(DrivingRequestVO request);

    ResponseVO get(String cityName, Integer total, Integer pageNum, Integer pageSize);

    ResponseVO getAll(String cityName) throws InterruptedException, IOException;

}
