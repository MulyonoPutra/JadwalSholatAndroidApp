package com.education.jadwalsholatapp.model;

public class ResponseDetailModel {

    private String status;
    private QueryModel query;
    private JadwalModel jadwal;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public QueryModel getQuery() {
        return query;
    }

    public void setQuery(QueryModel query) {
        this.query = query;
    }

    public JadwalModel getJadwal() {
        return jadwal;
    }

    public void setJadwal(JadwalModel jadwal) {
        this.jadwal = jadwal;
    }
}
