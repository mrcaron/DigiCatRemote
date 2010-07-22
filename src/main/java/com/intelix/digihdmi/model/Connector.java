package com.intelix.digihdmi.model;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Connector {

    @XStreamOmitField
    PropertyChangeSupport pcSupport = new PropertyChangeSupport(this);

    private String name;
    private int icon;
    private int index;     // 1-based

    public Connector() {
        this("",0,-1);
    }

    public Connector(String name, int icon, int index) {
        this.name = name;
        this.index = index;
        this.icon = icon;
        init();
    }

    public void init()
    {
        pcSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(String propName, PropertyChangeListener l)
    {
        pcSupport.addPropertyChangeListener(propName, l);
    }
    public void addPropertyChangeListener(PropertyChangeListener l)
    {
        pcSupport.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(String propName, PropertyChangeListener l)
    {
        pcSupport.removePropertyChangeListener(propName, l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l)
    {
        pcSupport.removePropertyChangeListener(l);
    }

    public int getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        pcSupport.firePropertyChange("name", this.name, name);
        this.name = name;
    }

    public void setIcon(int icon) {
        pcSupport.firePropertyChange("icon", this.icon, icon);
        this.icon = icon;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        pcSupport.firePropertyChange("index", this.index, index);
        this.index = index;
    }

}
