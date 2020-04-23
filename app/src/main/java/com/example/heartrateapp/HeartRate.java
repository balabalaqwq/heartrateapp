package com.example.heartrateapp;

public class HeartRate {
    private String time;
    private String heartrate;
    private String status;
    public HeartRate(String time, String heartrate, String status)  {
        this.time = time;
        this.heartrate = heartrate;
        this.status = status;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
