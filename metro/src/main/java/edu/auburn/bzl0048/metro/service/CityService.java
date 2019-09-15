package edu.auburn.bzl0048.metro.service;

import edu.auburn.bzl0048.metro.entity.City;

/**
 * @author liangboning
 * @date 2019/7/17 12:05
 */
public interface CityService {

    City getCityByCityCode(String cityCode);

    City getCityByCityName(String cityName);

}
