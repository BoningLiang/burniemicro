package edu.auburn.bzl0048.metro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.auburn.bzl0048.metro.entity.City;
import edu.auburn.bzl0048.metro.mapper.CityMapper;
import edu.auburn.bzl0048.metro.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liangboning
 * @date 2019/7/17 12:06
 */
@Service("cityService")
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public City getCityByCityCode(String cityCode) {
        QueryWrapper<City> cityQueryWrapper = new QueryWrapper<>();
        cityQueryWrapper.eq("city_code", cityCode);
        return cityMapper.selectOne(cityQueryWrapper);
    }

    @Override
    public City getCityByCityName(String cityName) {
        QueryWrapper<City> cityQueryWrapper = new QueryWrapper<>();
        cityQueryWrapper.eq("name", cityName);
        return cityMapper.selectOne(cityQueryWrapper);
    }
}
