package com.intelix.digihdmi.model;

import java.util.HashMap;
import java.util.logging.Logger;

public class Preset {

    private String name;
    private int index;
    private HashMap<Integer, Integer> connections;

    public Preset() {
        this.connections = new HashMap();
    }

    public Preset(String name, int index) {
        this();
        this.name = name;
        this.index = index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public HashMap<Integer, Integer> getConnections() {
        return connections;
    }

    public void setConnections(HashMap<Integer, Integer> connections) {
        if (connections.size() > 8)
        {
            Logger.getLogger(getClass().getName()).fine("oops, too many!");
        } else {
            this.connections = connections;
        }
    }

    public void makeConnection(int input, int output)
    {
        if (input > 7)
        {
            Logger.getLogger(getClass().getName()).fine("oops, too many!");
        } else { 
            this.connections.put(output, input);
        }
    }
}