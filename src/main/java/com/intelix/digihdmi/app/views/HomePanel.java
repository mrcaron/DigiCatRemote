 package com.intelix.digihdmi.app.views;
 
import com.intelix.digihdmi.app.DigiHdmiApp;
 import java.awt.Dimension;
 import javax.swing.ActionMap;
 import javax.swing.JButton;
 import javax.swing.JPanel;
 import org.jdesktop.application.Application;
 import org.jdesktop.application.ResourceMap;
 import org.jdesktop.layout.GroupLayout;

 public class HomePanel extends JPanel
 {
   private JButton btnAdmin;
   private JButton btnLock;
   private JButton btnMatrixView;
   private JButton btnPresetView;
   private JButton btnRoomView;
 
   public HomePanel()
   {
     initComponents();
   }
 
   private void initComponents()
   {
     btnRoomView = new JButton();
     btnMatrixView = new JButton();
     btnPresetView = new JButton();
     btnAdmin = new JButton();
     btnLock = new JButton();
 
     setMinimumSize(new Dimension(600, 400));
     setName("Form");
 
     ActionMap actionMap = ((DigiHdmiApp)Application.getInstance(DigiHdmiApp.class)).getContext().getActionMap(HomePanel.class, this);
     btnRoomView.setAction(actionMap.get("showRoomView"));
     ResourceMap resourceMap = ((DigiHdmiApp)Application.getInstance(DigiHdmiApp.class)).getContext().getResourceMap(HomePanel.class);
     btnRoomView.setText(resourceMap.getString("btnRoomView.text", new Object[0]));
     btnRoomView.setName("btnRoomView");
 
     btnMatrixView.setAction(actionMap.get("showMatrixView"));
     btnMatrixView.setText(resourceMap.getString("btnMatrixView.text", new Object[0]));
     btnMatrixView.setName("btnMatrixView");
 
     btnPresetView.setAction(actionMap.get("showPresetListView"));
     btnPresetView.setText(resourceMap.getString("btnPresetView.text", new Object[0]));
     btnPresetView.setName("btnPresetView");
 
     btnAdmin.setAction(actionMap.get("showUtilView"));
     btnAdmin.setText(resourceMap.getString("btnAdmin.text", new Object[0]));
     btnAdmin.setName("btnAdmin");
 
     btnLock.setAction(actionMap.get("lockApp"));
     btnLock.setText(resourceMap.getString("btnLock.text", new Object[0]));
     btnLock.setName("btnLock");
 
     GroupLayout layout = new GroupLayout(this);
     setLayout(layout);
     layout.setHorizontalGroup(layout.createParallelGroup(1).add(layout.createSequentialGroup().addContainerGap(433, 32767).add(btnLock).addPreferredGap(1).add(btnAdmin).addContainerGap()).add(layout.createSequentialGroup().add(238, 238, 238).add(layout.createParallelGroup(4).add(btnMatrixView).add(btnRoomView).add(btnPresetView)).addContainerGap(245, 32767)));
 
     layout.setVerticalGroup(layout.createParallelGroup(1).add(2, layout.createSequentialGroup().add(115, 115, 115).add(btnRoomView).add(18, 18, 18).add(btnMatrixView).add(18, 18, 18).add(btnPresetView).addPreferredGap(0, 116, 32767).add(layout.createParallelGroup(3).add(btnAdmin).add(btnLock)).addContainerGap()));
   }
 }