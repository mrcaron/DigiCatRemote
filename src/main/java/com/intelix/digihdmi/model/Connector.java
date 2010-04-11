package com.intelix.digihdmi.model;

public class Connector {

    private String name;
    private String icon;
    private int index;     // 1-based

    public Connector() {
    }

    public Connector(String name, String icon, int index) {
        this.name = name;
        this.index = index;
        this.icon = icon;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
