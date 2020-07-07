package com.century.vo;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Ticket {
    private String airCode;
    private String company;
    private String airStartDrome;
    private String arriveDrome;
    private String startTime;
    private String arriveTime;
    private String mode;
    private String airStop;
    private String week;

    public String getAirCode() {
        return airCode;
    }

    public void setAirCode(String airCode) {
        this.airCode = airCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAirStartDrome() {
        return airStartDrome;
    }

    public void setAirStartDrome(String airStartDrome) {
        this.airStartDrome = airStartDrome;
    }

    public String getArriveDrome() {
        return arriveDrome;
    }

    public void setArriveDrome(String arriveDrome) {
        this.arriveDrome = arriveDrome;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getAirStop() {
        return airStop;
    }

    public void setAirStop(String airStop) {
        this.airStop = airStop;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

}
