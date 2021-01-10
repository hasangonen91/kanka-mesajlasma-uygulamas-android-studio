package com.example.kanka;

import org.json.JSONException;
import org.json.JSONObject;

public class MesajModel {

    private String gonderen,mesaj;



    public MesajModel(){

    }
    public MesajModel(String gonderen, String mesaj) {
        this.gonderen = gonderen;
        this.mesaj = mesaj;
    }
    public String getGonderen() {
        return gonderen;
    }
    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }
    public String getMesaj() {
        return mesaj;
    }
    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("gonderen", gonderen);
            object.put("mesaj", mesaj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}