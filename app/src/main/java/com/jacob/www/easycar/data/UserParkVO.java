package com.jacob.www.easycar.data;

/**
 * Created by 张兴锐 on 2017/12/12.
 */

public class UserParkVO  {


    /**
     * userPark : {"userId":"1511317484410820796","garageId":"1234","parkId":1}
     * garageInfo : {"garageId":"1234","garageName":"老图书馆车库","parkingLotCount":3,"freeParkingLotCount":3,"positionLongitude":106.60642,"positionLatitude":29.53131,"parkingLotInfo":"111"}
     */

    private UserParkBean userPark;
    private GarageInfoBean garageInfo;

    public UserParkBean getUserPark() {
        return userPark;
    }

    public void setUserPark(UserParkBean userPark) {
        this.userPark = userPark;
    }

    public GarageInfoBean getGarageInfo() {
        return garageInfo;
    }

    public void setGarageInfo(GarageInfoBean garageInfo) {
        this.garageInfo = garageInfo;
    }

    public static class UserParkBean {
        /**
         * userId : 1511317484410820796
         * garageId : 1234
         * parkId : 1
         */

        private String userId;
        private String garageId;
        private int parkId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGarageId() {
            return garageId;
        }

        public void setGarageId(String garageId) {
            this.garageId = garageId;
        }

        public int getParkId() {
            return parkId;
        }

        public void setParkId(int parkId) {
            this.parkId = parkId;
        }
    }

    public static class GarageInfoBean {
        /**
         * garageId : 1234
         * garageName : 老图书馆车库
         * parkingLotCount : 3
         * freeParkingLotCount : 3
         * positionLongitude : 106.60642
         * positionLatitude : 29.53131
         * parkingLotInfo : 111
         */

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
