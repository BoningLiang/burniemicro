package io.github.burnieliang.metro.utils;

import io.github.burnieliang.metro.vo.baidu.Location;
import io.github.burnieliang.metro.vo.baidu.query.ResponseVO;
import io.github.burnieliang.metro.vo.baidu.query.Result;
import io.github.burnieliang.metro.vo.metroLine.Line;
import io.github.burnieliang.metro.vo.metroLine.Station;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MetroUtil {

    public List<Line> parseLines(ResponseVO responseVO) {
        List<Line> lines = new ArrayList<>();

        List<Result> results = responseVO.getResults();
        for (int i = 0; i < results.size(); i++) {
            String address = results.get(i).getAddress();
            String[] addresses = address.split(";");
            for (int j = 0; j < addresses.length; j++) {
                if (findLine(lines, addresses[j])) {
                    for (int k = 0; k < lines.size(); k++) {
                        if (lines.get(k).getName().equals(addresses[j])) {
                            Station station = new Station();
                            station.setName(results.get(i).getName());
                            station.setLocation(results.get(i).getLocation());
                            lines.get(k).getStations().add(station);
                        }
                    }
                } else {
                    Line line = new Line();
                    line.setName(addresses[j]);
                    line.setCity(results.get(i).getCity());
                    List<Station> stations = new ArrayList<>();
                    Station station = new Station();
                    station.setName(results.get(i).getName());
                    station.setLocation(results.get(i).getLocation());
                    stations.add(station);
                    line.setStations(stations);
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    private boolean findLine(List<Line> lines, String lineName) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getName().equals(lineName)) {
                return true;
            }
        }
        return false;
    }


    public void sort(List<Line> lines) throws Exception {
        List<Line> sortedLines = new ArrayList<>();
        for (Line line: lines) {
            List<Station> stations = sortStations(line.getStations());
            line.setStations(stations);
        }
    }

    public List<Station> sortStations(List<Station> stations) throws Exception {
        List<Station> sortedStations = new LinkedList<>();
        int stationCount = stations.size();

        int direction = 1;

        Station currentStation = null;
        Station startStation = null;

        for (int i = 0; ; i++) {
            if (sortedStations.size() == stationCount) {
                break;
            }
            if (i == 0) {
                sortedStations.add(stations.get(i));
                currentStation = new Station();
                currentStation.setName(stations.get(i).getName());
                currentStation.setLocation(stations.get(i).getLocation());
                startStation = new Station();
                startStation.setName(stations.get(i).getName());
                startStation.setLocation(stations.get(i).getLocation());
                stations.remove(i);
            } else {
                double minDistance = 100000000;
                int flag = -1;
                for (int j = 0; j < stations.size(); j++) {
                    Station station1 = currentStation;
                    Station station2 = stations.get(j);
                    Location location1 = station1.getLocation();
                    Location location2 = station2.getLocation();
                    double distance = getDistance(location1.getLat(), location1.getLng(), location2.getLat(), location2.getLng());
                    if (distance < minDistance) {
                        minDistance = distance;
                        flag = j;
                    }
                }
                if (direction > 0
                        && !currentStation.getName().equals(startStation.getName())
                        && minDistance > getDistance(currentStation.getLocation().getLat(), currentStation.getLocation().getLng(), startStation.getLocation().getLat(), startStation.getLocation().getLng())) {
                    currentStation = startStation;
                    direction = -direction;
                    continue;
                }
                System.out.println(currentStation.getName() + " " + stations.get(flag).getName() + " " + minDistance + " direction " + direction);
                if (flag >= 0) {
                    if (direction == 1) {
                        sortedStations.add(stations.get(flag));
                    } else {
                        sortedStations.add(0, stations.get(flag));
                    }
                    currentStation = new Station();
                    currentStation.setName(stations.get(flag).getName());
                    currentStation.setLocation(stations.get(flag).getLocation());
                    stations.remove(flag);
                } else {
                    throw new Exception("排序错误2");
                }
            }
        }
        return sortedStations;
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);

        double s = 2 * Math.asin((Math.sqrt(Math.pow(Math.sin(a/2), 2)
        + Math.cos(radLat1) * Math.cos(radLat2)
        * Math.pow(Math.sin(b/2), 2))));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10;
        return s;
    }

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    private static double EARTH_RADIUS = 6378.137;

}
