package com.jacob.www.easycar.data;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/11/17.
 */

public class GarageBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String garageId;
        private String garageName;
        private int parkingLotCount;
        private int freeParkingLotCount;
        private double positionLongitude;
        private double positionLatitude;
        private String parkingLotInfo;

        public String getGarageId() {
            return garageId;
        }

        public void setGarageId(String garageId) {
            this.garageId = garageId;
        }

        public String getGarageName() {
            return garageName;
        }

        public void setGarageName(String garageName) {
            this.garageName = garageName;
        }

        public int getParkingLotCount() {
            return parkingLotCount;
        }

        public void setParkingLotCount(int parkingLotCount) {
            this.parkingLotCount = parkingLotCount;
        }

        public int getFreeParkingLotCount() {
            return freeParkingLotCount;
        }

        public void setFreeParkingLotCount(int freeParkingLotCount) {
            this.freeParkingLotCount = freeParkingLotCount;
        }

        public double getPositionLongitude() {
            return positionLongitude;
        }

        public void setPositionLongitude(double positionLongitude) {
            this.positionLongitude = positionLongitude;
        }

        public double getPositionLatitude() {
            return positionLatitude;
        }

        public void setPositionLatitude(double positionLatitude) {
            this.positionLatitude = positionLatitude;
        }

        public String getParkingLotInfo() {
            return parkingLotInfo;
        }

        public void setParkingLotInfo(String parkingLotInfo) {
            this.parkingLotInfo = parkingLotInfo;
        }
    }
}
