package com.jacob.www.easycar.data;

/**
 * Created by ASUS-NB on 2017/11/20.
 */

public class GarageLotBean {
    /**
     * code : 0
     * data : {"freeParkingLotCount":0,"garageId":"string","garageName":"string","parkingLotCount":0,"parkingLotInfo":"string","positionLatitude":0,"positionLongitude":0}
     * message : string
     */

    private int code;
    /**
     * freeParkingLotCount : 0
     * garageId : string
     * garageName : string
     * parkingLotCount : 0
     * parkingLotInfo : string
     * positionLatitude : 0
     * positionLongitude : 0
     */

    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private int freeParkingLotCount;
        private String garageId;
        private String garageName;
        private int parkingLotCount;
        private String parkingLotInfo;
        private double positionLatitude;
        private double positionLongitude;

        public int getFreeParkingLotCount() {
            return freeParkingLotCount;
        }

        public void setFreeParkingLotCount(int freeParkingLotCount) {
            this.freeParkingLotCount = freeParkingLotCount;
        }

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

        public String getParkingLotInfo() {
            return parkingLotInfo;
        }

        public void setParkingLotInfo(String parkingLotInfo) {
            this.parkingLotInfo = parkingLotInfo;
        }

        public double getPositionLatitude() {
            return positionLatitude;
        }

        public void setPositionLatitude(double positionLatitude) {
            this.positionLatitude = positionLatitude;
        }

        public double getPositionLongitude() {
            return positionLongitude;
        }

        public void setPositionLongitude(double positionLongitude) {
            this.positionLongitude = positionLongitude;
        }
    }
}
