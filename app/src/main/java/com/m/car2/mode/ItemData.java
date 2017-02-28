package com.m.car2.mode;

/**
 * Created by lbe on 17-2-27.
 */

public class ItemData {

    private String name;
    private int iconRecource;
    private int id;

    public ItemData() {
    }

    public ItemData(String name, int iconRecource, int id) {
        this.name = name;
        this.iconRecource = iconRecource;
        this.id = id;
    }

    public ItemData(String name, int iconRecource) {
        this.name = name;
        this.iconRecource = iconRecource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRecource() {
        return iconRecource;
    }

    public void setIconRecource(int iconRecource) {
        this.iconRecource = iconRecource;
    }

    public int getId() {
        return id;
    }
}
