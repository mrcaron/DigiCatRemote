 package com.intelix.digihdmi.app.actions;
 
import com.intelix.digihdmi.app.tasks.LoadPresetsListTask;
 import javax.swing.JOptionPane;
 import org.jdesktop.application.Action;
 import org.jdesktop.application.Application;
 import org.jdesktop.application.Task;
 
 public class PresetActions
 {
   @Action
   public Task showPresetListForLoad()
   {
     return new LoadPresetsListTask(Application.getInstance());
   }
 
   @Action
   public Task showPresetListForSave() {
     return null;
   }
 
   @Action
   public Task applyPresetAndShowMatrixView() {
     JOptionPane.showMessageDialog(null, "Applying preset.");
 
     return null;
   }
 }

/* Location:           /Users/developer/Downloads/dist/DigiHdmiApp-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.intelix.digihdmiapp.PresetActions
 * JD-Core Version:    0.5.4
 */