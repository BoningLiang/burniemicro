package io.github.burnieliang.metro.service;

import io.github.burnieliang.metro.entity.City;

/**
 * @author liangboning
 * @date 2019/7/17 12:05
 */
public interface CityService {

    City getCityByCityCode(String cityCode);

    City getCityByCityName(String cityName);

}
