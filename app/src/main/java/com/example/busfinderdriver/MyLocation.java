package com.example.busfinderdriver;

public class MyLocation {

        private double latitude;
        private double longitude;

        public MyLocation(double longitude,double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }
        public MyLocation(){}

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

}
