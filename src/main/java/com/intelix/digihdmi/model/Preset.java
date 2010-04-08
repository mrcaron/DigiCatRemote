package com.intelix.digihdmi.model;

 
 
 import java.util.HashMap;
 
 public class Preset
 {
   private String name;
   private int index;
   private HashMap<Integer, Integer> connections;
 
   public Preset()
   {
     this.connections = new HashMap();
   }
 
   public Preset(String name, int index)
   {
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
 }

/* Location:           /Users/developer/Downloads/dist/DigiHdmiApp-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.intelix.hdmimodel.Preset
 * JD-Core Version:    0.5.4
 */