package io.github.burnieliang.metro.service.impl;

import com.couchbase.client.java.document.JsonDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.burnieliang.metro.dao.BaseDao;
import io.github.burnieliang.metro.dao.CouchbaseDaoImpl;
import io.github.burnieliang.metro.service.BaiduService;
import io.github.burnieliang.metro.vo.baidu.DrivingOutput;
import io.github.burnieliang.metro.vo.baidu.DrivingRequestVO;
import io.github.burnieliang.metro.vo.baidu.query.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class BaiduServiceImpl implements BaiduService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${baidu.ak:}")
    private String ak;

    @Override
    public DrivingOutput get(DrivingRequestVO request) {

        String url = "http://api.map.baidu.com/direction/v2/driving?";
        String origin = "origin="+request.getOrigin().getLat()+","+request.getOrigin().getLng();
        String destination = "destination="+request.getDestination().getLat()+","+request.getDestination().getLng();
        String ak = "ak="+request.getAk();
        String coordType = "coord_type="+request.getCoordType();
        url = url + origin + "&" + destination + "&" + ak + "&" + coordType;
        ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity(url, String.class);
        try {
            return objectMapper.readValue(stringResponseEntity.getBody(), DrivingOutput.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseVO get(String cityName, Integer total, Integer pageNum, Integer pageSize) {
        String url = "http://api.map.baidu.com/place/v2/search?";
        String region = "region=" + cityName;
        String output = "&output=json";
        String scope = "&scope=2";
        String tag = "&tag=地铁";
        String theAk = "&ak="+ak;
        String query = "&query=地铁站";
        url = url + region + output + scope + tag + theAk + query;
        if (total == null) {
            url = url + "&page_num=0";
        } else {
            url = url + "&page_num=" + pageNum.toString();
        }
        url = url + "&page_size=" + pageSize.toString();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        try {
            return objectMapper.readValue(responseEntity.getBody(), ResponseVO.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseVO getAll(String cityName) throws InterruptedException, IOException {

        BaseDao baseDao = new CouchbaseDaoImpl("", "", "", "");

        JsonDocument jsonDocument = baseDao.get(cityName);
        if (jsonDocument != null) {
            String storedData = jsonDocument.content().toString();
            return objectMapper.readValue(storedData, ResponseVO.class);
        }

        Integer pageSize = 20;
        Integer pageNum = 0;
        ResponseVO responseVO = get(cityName, null, null, pageSize);
        System.out.println("count : " + pageNum.toString() + " = " + responseVO.getResults().size());
        if (responseVO != null) {
            Integer total = responseVO.getTotal();
            if (total <= pageSize) {
                baseDao.upsert(responseVO, cityName);
                return responseVO;
            } else {
                for (;;) {
                    TimeUnit.SECONDS.sleep(1);
                    pageNum++;
                    ResponseVO responseVO1 = get(cityName, total, pageNum, pageSize);
                    if (responseVO1 != null && responseVO1.getResults() != null) {
                        if (responseVO1.getResults().size() > 0) {
                            responseVO.getResults().addAll(responseVO1.getResults());
                            System.out.println("count : " + pageNum.toString() + " = " + responseVO1.getResults().size());
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (responseVO.getResults().size() > 0) {
                baseDao.upsert(responseVO, cityName);
                return responseVO;
            }
        }
        return null;
    }
}
