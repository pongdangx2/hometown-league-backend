package me.lkh.hometownleague.common.util;

public class LocationUtil {

    /**
     * 두 좌표 간 거리 계산
     * @param lat1 첫번쨰 좌표의 위도
     * @param lon1 첫번째 좌표의 경도
     * @param lat2 두번째 좌표의 위도
     * @param lon2 두번째 좌표의 경도
     * @return 좌표간 거리 (km)
     */
    public static double getDistance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;

        return dist; //단위 : km
    }

    //10진수를 radian(라디안)으로 변환
    private static double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }

    //radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }

}
