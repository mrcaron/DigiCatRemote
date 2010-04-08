 package com.intelix.digihdmi.app.views;
 
 import java.awt.event.ItemEvent;
 import java.awt.event.ItemListener;
 import javax.swing.AbstractButton;
 import javax.swing.JToggleButton;
 
 public class InputButtonContainerPanel extends ButtonContainerPanel
   implements ItemListener
 {
   protected AbstractButton selectedButton;
 
   public InputButtonContainerPanel()
   {
     this.selectedButton = null;
   }
 
   protected AbstractButton createButton(String name, String iconName) {
     JToggleButton b = new JToggleButton(name);
     b.addItemListener(this);
     return b;
   }
 
   public void itemStateChanged(ItemEvent e)
   {
     AbstractButton b = (AbstractButton)e.getSource();
     int state = e.getStateChange();
     if (state == 1) {
       if (this.selectedButton != null)
         this.selectedButton.setSelected(false);
       this.selectedButton = ((JToggleButton)b);
     }
   }
 }

/* Location:           /Users/developer/Downloads/dist/DigiHdmiApp-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.intelix.digihdmiapp.InputButtonContainerPanel
 * JD-Core Version:    0.5.4
 */