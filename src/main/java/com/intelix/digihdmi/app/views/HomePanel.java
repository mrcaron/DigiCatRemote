package com.intelix.digihdmi.app.views;

import com.intelix.digihdmi.app.DigiHdmiApp;
import java.awt.Dimension;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.layout.GroupLayout;

public class HomePanel extends JPanel {

    private JButton btnAdmin;
    private JButton btnLock;
    private JButton btnMatrixView;
    private JButton btnPresetView;
    private JButton btnRoomView;

    public HomePanel() {
        initComponents();
    }

    private void initComponents() {
        btnRoomView = new JButton();
        btnMatrixView = new JButton();
        btnPresetView = new JButton();
        btnAdmin = new JButton();
        btnLock = new JButton();

        setMinimumSize(new Dimension(600, 400));
        setName("Form");

        ActionMap actionMap = ((DigiHdmiApp) Application.getInstance(DigiHdmiApp.class)).getContext().getActionMap(HomePanel.class, this);
        btnRoomView.setAction(actionMap.get("showRoomView"));
        ResourceMap resourceMap = ((DigiHdmiApp) Application.getInstance(DigiHdmiApp.class)).getContext().getResourceMap(HomePanel.class);
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

        setLayout(new MigLayout(
                (System.getProperty("DEBUG_UI") == null ? "" : "debug,") +
                "al 50% 50%, gapy 10"));

        this.add(btnRoomView, "wrap");
        this.add(btnMatrixView, "wrap");
        this.add(btnPresetView, "wrap");
        this.add(btnLock, "id blk, pos (visual.x2 - pref) (visual.y2 - pref)");
        this.add(btnAdmin, "pos (blk.x - pref) (blk.y)");
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Home Panel");
                System.setProperty("DEBUG_UI", "true");
                HomePanel lv = new HomePanel();
                f.getContentPane().add(lv);
                f.setSize(700,400);
                f.setVisible(true);
            }
        });
    }
}
