package com.example.ahmed.rites.Pojo;

/**
 * Created by ahmed on 2/23/2016.
 */
public class Rite {
    private String name;
    private String info;
    private int id;

    public int getId() {
        return id;
    }

    public Rite(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
