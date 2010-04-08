 package com.intelix.digihdmi.app.views;
 
import com.intelix.digihdmi.app.DigiHdmiApp;
 import java.awt.event.ActionEvent;
 import javax.swing.AbstractAction;
 import javax.swing.JButton;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import org.jdesktop.application.Application;
 import org.jdesktop.application.FrameView;
 
 public class PresetLoadListView extends ButtonListView
 {
   protected void initializeHomePanel()
   {
     super.initializeHomePanel();
 
     JButton matrixView = new JButton();
 
     matrixView.setAction(new AbstractAction("MatrixView") {
       public void actionPerformed(ActionEvent e) {
         JOptionPane.showMessageDialog(((DigiHdmiApp)Application.getInstance()).getMainView().getFrame(), "Not so fast. This isn't implemented yet!");
       }
       public boolean isEnabled() {
         return true;
       }
     });
     this.homePanel.add(matrixView);
   }
 }

/* Location:           /Users/developer/Downloads/dist/DigiHdmiApp-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.intelix.digihdmiapp.PresetLoadListView
 * JD-Core Version:    0.5.4
 */