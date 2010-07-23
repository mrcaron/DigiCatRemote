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

    public void setName(String n) {
        String oldname = this.name;
        this.name = n;
        pcSupport.firePropertyChange("name", oldname, n);
    }

    public void setIcon(int i) {
        int old = this.icon;
        this.icon = i;
        pcSupport.firePropertyChange("icon", old, i);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int i) {
        pcSupport.firePropertyChange("index", this.index, i);
        this.index = i;
    }

}
