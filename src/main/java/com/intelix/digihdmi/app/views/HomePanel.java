package com.intelix.digihdmi.app.views;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.util.IconImageButton;
import com.intelix.digihdmi.util.PaintedJPanel;
import com.intelix.digihdmi.util.TextImageButton;
import java.awt.Dimension;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

public class HomePanel extends PaintedJPanel {

    private JButton btnAdmin;
    private JButton btnLock;
    private JButton btnMatrixView;
    private JButton btnPresetView;
    private JButton btnRoomView;

    private ResourceMap resourceMap;

    public HomePanel() {
        resourceMap = ((DigiHdmiApp) Application.getInstance(DigiHdmiApp.class)).getContext().getResourceMap(HomePanel.class);
        initializeComponents();
    }

    protected void initializeComponents() {
        btnRoomView = new TextImageButton("RoomViewBtn");
        btnRoomView.setName("btnRoomView");
        btnMatrixView = new TextImageButton("MatrixViewBtn");
        btnMatrixView.setName("btnMatrixView");
        btnPresetView = new TextImageButton("PresetViewBtn");
        btnPresetView.setName("btnPresetView");
        btnAdmin = new IconImageButton("UtilBtn");;
        btnAdmin.setName("btnAdmin");
        btnLock = new IconImageButton("LockBtn");
        btnLock.setName("btnLock");


        setMinimumSize(new Dimension(600, 400));
        setName("Form");

        setLayout(new MigLayout(
                (System.getProperty("DEBUG_UI") == null ? "" : "debug,") +
                "al 50% 50%, gapy 10"));

        this.add(btnRoomView, "wrap");
        this.add(btnMatrixView, "wrap");
        this.add(btnPresetView, "wrap");
        this.add(btnLock, "id blk, pos (visual.x2 - pref) (visual.y2 - pref)");
        this.add(btnAdmin, "pos (blk.x - pref) (blk.y)");
        
    }

    public void setLockAction(Action a) { setBtnAction(btnLock, a); }
    public void setAdminAction(Action a) { setBtnAction(btnAdmin, a); }
    public void setPresetViewAction(Action a) { setBtnAction(btnPresetView, a); }
    public void setMatrixViewAction(Action a) { setBtnAction(btnMatrixView, a); }
    public void setRoomViewAction(Action a) { setBtnAction(btnRoomView, a); }
    private void setBtnAction(JButton b, Action a)
    {
        b.setAction(a);
        resourceMap.injectComponent(b);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Home Panel");
                //System.setProperty("DEBUG_UI", "true");
                HomePanel lv = new HomePanel();
                f.getContentPane().add(lv);
                f.setSize(700,400);
                f.setVisible(true);
            }
        });
    }
}
