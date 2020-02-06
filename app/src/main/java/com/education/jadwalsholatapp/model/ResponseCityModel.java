package com.education.jadwalsholatapp.model;

import java.util.ArrayList;

public class ResponseCityModel {

    private String status;
    private ArrayList<CityModel> kota;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<CityModel> getKota() {
        return kota;
    }

    public void setKota(ArrayList<CityModel> kota) {
        this.kota = kota;
    }
}
