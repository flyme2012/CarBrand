package com.m.car2.mode;

/**
 * Created by hubing on 2017/3/11.
 */

public class CarData {

    private String carName;
    private String carIconUrl;
    private String carDesUrl;
    private String carId;
    private String carSimpDes;


    public String getCarSimpDes() {
        return carSimpDes;
    }

    public void setCarSimpDes(String carSimpDes) {
        this.carSimpDes = carSimpDes;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarIconUrl() {
        return carIconUrl;
    }

    public void setCarIconUrl(String carIconUrl) {
        this.carIconUrl = carIconUrl;
    }

    public String getCarDesUrl() {
        return carDesUrl;
    }

    public void setCarDesUrl(String carDesUrl) {
        this.carDesUrl = carDesUrl;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }


    @Override
    public String toString() {
        return "CarData{" +
                "carName='" + carName + '\'' +
                ", carIconUrl='" + carIconUrl + '\'' +
                ", carDesUrl='" + carDesUrl + '\'' +
                ", carId='" + carId + '\'' +
                ", carSimpDes='" + carSimpDes + '\'' +
                '}';
    }
}
