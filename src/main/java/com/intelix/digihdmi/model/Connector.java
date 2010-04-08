package com.intelix.digihdmi.model;

 
 
 public class Connector
 {
   public String name;
   public String icon;
 
   public Connector()
   {
   }
 
   public Connector(String name, String icon)
   {
     this.name = name; this.icon = icon;
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
 }

/* Location:           /Users/developer/Downloads/dist/DigiHdmiApp-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.intelix.hdmimodel.Connector
 * JD-Core Version:    0.5.4
 */