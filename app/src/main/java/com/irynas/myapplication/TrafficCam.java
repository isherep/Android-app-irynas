package com.irynas.myapplication;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class TrafficCam {


    private String camLabel;
    private String imageUrl;
    private String type;
    private double[] coords;

    private Map<String, String> baseUrl = new HashMap<String, String>();

    public TrafficCam(String camLabel, String imageUrl, String type, double[] coords){
        this.camLabel = camLabel;
        this.imageUrl = imageUrl;
        this.type = type;
        this.coords = coords;
    }


    public String imageUrl() {
        return this.imageUrl;
    }

    public String getLabel(){
        return this.camLabel;
    }

    public double[] getCoords(){
        return this.coords;
    }
}
