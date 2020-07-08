package com.century.vo;

//出发地（startCity）、目的地（arriveCity）、出发日期(date)
public class SAD {
    private String startCity;
    private String arriveCity;
    private String theDate;

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    public String getTheDate() {
        return theDate;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }
}
