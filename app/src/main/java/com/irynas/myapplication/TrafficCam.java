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

    public void createMap() {

        baseUrl.put("sdot", "http://www.seattle.gov/trafficcams/images/");
        baseUrl.put("wsdot", "http://images.wsdot.wa.gov/nw/");
    }

    public String imageUrl() {
        return baseUrl.get(this.type) + this.imageUrl;
    }

    public String getLabel(){
        return baseUrl.get(this.type)  + this.camLabel;
    }
}
