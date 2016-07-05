package com.example.ahmed.rites.Pojo;

import java.io.Serializable;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ahmed on 2/19/2016.
 */
public class Friend implements Serializable{
    private String name;
    private int id;
    private LatLng location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
