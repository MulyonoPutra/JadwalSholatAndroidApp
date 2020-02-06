package com.education.jadwalsholatapp.model;

//{
//        "status": "ok",
//        "query": {
//        "format": "json",
//        "kota": "667",
//        "tanggal": "2020-01-01"
//        },
//        "jadwal": {
//        "status": "ok",
//        "data": {
//        "ashar": "15:25",
//        "dhuha": "06:07",
//        "dzuhur": "11:59",
//        "imsak": "04:08",
//        "isya": "19:28",
//        "maghrib": "18:13",
//        "subuh": "04:18",
//        "tanggal": "Rabu, 01 Jan 2020",
//        "terbit": "05:39"
//        }
//        }
//        }

public class JadwalModel {
    private String status;
    private DataModel data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }
}
