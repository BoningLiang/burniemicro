package io.github.burnieliang.metro.controller;

import io.github.burnieliang.common.entity.Resp;
import io.github.burnieliang.metro.config.Constance;
import io.github.burnieliang.metro.service.BaiduService;
import io.github.burnieliang.metro.utils.CoordDistanceUtil;
import io.github.burnieliang.metro.utils.PositionConvertUtil;
import io.github.burnieliang.metro.vo.baidu.*;
import io.github.burnieliang.metro.vo.xcode.GpxVO;
import io.github.burnieliang.metro.vo.xcode.WptVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.util.StrUtil;

@RestController
@Slf4j
@RequestMapping("/gpx")
public class GpxController {

    @Autowired
    private BaiduService baiduService;

    @PostMapping(value = "/gpx", produces = MediaType.APPLICATION_XML_VALUE)
    public GpxVO getGpx(@RequestBody DrivingRequestVO request) {
//        DrivingRequestVO request = new DrivingRequestVO();

        if (StrUtil.isBlank(request.getAk())) {
            request.setAk(Constance.baiduAk);
        }
        if (StrUtil.isBlank(request.getCoordType())) {
            request.setCoordType("wgs84");
        }
        
//        Location origin = new Location();
//        origin.setLat(37.8910158318);
//        origin.setLng(114.6481650080);
//        Location destination = new Location();
//        destination.setLat(37.8982864447);
//        destination.setLng(114.6323964002);
//        request.setOrigin(origin);
//        request.setDestination(destination);
        DrivingOutput output = baiduService.get(request);

        if (output != null) {

            if (output.getResult().getRoutes().size() > 0) {
                int duration = output.getResult().getRoutes().get(0).getDuration();
                int distance = output.getResult().getRoutes().get(0).getDistance();
                double speed = distance / duration; // m/s

                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                //wpt.time = "2014-09-24T14:55:00Z";

                GpxVO gpx = new GpxVO();
                List<WptVO> wpts = new ArrayList<>();
                List<Routes> routes = output.getResult().getRoutes();
                Integer count = 0;
                Location lastLocation = new Location();
                for (int i = 0 ; i < routes.size() && i < 1 ; i++) {
                    List<Steps> steps = routes.get(i).getSteps();
                    for (int j = 0 ; j < steps.size() ; j++) {
                        List<String> paths = Arrays.asList(steps.get(j).getPath().split(";"));
                        for (int k = 0; k < paths.size(); k++) {
                            PositionConvertUtil util = new PositionConvertUtil();
                            String[] coord = paths.get(k).split(",");
                            WptVO wpt = new WptVO();

                            CoordinateBean coordinateBean = util.baidu09ToWgs84(Double.valueOf(coord[1]), Double.valueOf(coord[0]));
                            wpt.name = count.toString();
                            if (count > 0) {
                                CoordDistanceUtil coordDistanceUtil = new CoordDistanceUtil();
                                double distanceBetweenCoords = coordDistanceUtil.getDistance(coordinateBean.getLatitude(), coordinateBean.getLongitude(), lastLocation.getLat(), lastLocation.getLng());
                                wpt.name = String.valueOf(distance);
                                long secondsToPlus = (long)(distanceBetweenCoords / speed);
                                localDateTime = localDateTime.plusSeconds(secondsToPlus);
                                wpt.time = localDateTime.format(formatter);
                            } else {
                                wpt.time = localDateTime.format(formatter);
                            }

                            wpt.lon = String.valueOf(coordinateBean.getLongitude());
                            wpt.lat = String.valueOf(coordinateBean.getLatitude());
                            lastLocation.setLng(coordinateBean.getLongitude());
                            lastLocation.setLat(coordinateBean.getLatitude());

                            //wpt.time = "2014-09-24T14:55:00Z";
                            wpts.add(wpt);
                            count++;
                        }
                    }
                }
//            output.getResult().getRoutes().forEach(routes -> {
//                routes.getSteps().forEach(steps -> {
//                    List<String> paths = Arrays.asList(steps.getPath().split(";"));
//
//                    paths.forEach(coords->{
//                        String[] coord = coords.split(",");
//                        WptVO wpt = new WptVO();
//                        wpt.lon = coord[0];
//                        wpt.lat = coord[1];
//                        wpt.name = "a";
//                        wpt.time = "2014-09-24T14:55:00Z";
//                        wpts.add(wpt);
//                    });
//                });
//            });
                gpx.wpt = wpts;
                gpx.version = "1.1";
                gpx.creator = "Xcode";
                return gpx;


            }


        }

        return null;
    }

    @GetMapping(value = "/testBaiduDrivingPath")
    public Resp getPath() {
        DrivingRequestVO request = new DrivingRequestVO();
        request.setAk(Constance.baiduAk);
        request.setCoordType("wgs84");
        Location origin = new Location();
        origin.setLat(37.8910158318);
        origin.setLng(114.6481650080);
        Location destination = new Location();
        destination.setLat(37.8982864447);
        destination.setLng(114.6323964002);
        request.setOrigin(origin);
        request.setDestination(destination);
        return new Resp<>(baiduService.get(request));
    }

    @GetMapping(value = "/gpx.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public GpxVO get() {
        GpxVO gpx = new GpxVO();
        List<WptVO> wpt = new ArrayList<>();

        WptVO wptVO1 = new WptVO();
        wptVO1.lat = "37.8910158318";
        wptVO1.lon = "114.6481650080";
        wptVO1.name = "栾城图书馆";
        wptVO1.time = "2014-09-24T14:55:00Z";

        wpt.add(wptVO1);

        gpx.wpt = wpt;
        gpx.version = "1.1";
        gpx.creator = "Xcode";

        return gpx;
    }

}
