/*
 * DigiHdmiApp.java
 */
package com.intelix.digihdmi.app;

import com.intelix.digihdmi.app.actions.ConnectorActions;
import com.intelix.digihdmi.app.actions.MenuActions;
import com.intelix.digihdmi.app.actions.PresetActions;
import com.intelix.digihdmi.app.views.PresetLoadListView;
import com.intelix.digihdmi.app.views.RoomSelectionView;
import com.intelix.digihdmi.app.views.DigiHdmiAppMainView;
import com.intelix.digihdmi.app.views.ButtonListView;
import com.intelix.digihdmi.app.views.HomePanel;
import com.intelix.digihdmi.app.views.RoomView;
import com.intelix.digihdmi.model.Device;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class DigiHdmiApp extends SingleFrameApplication {

    JComponent currentView;
    JComponent homeView;
    JComponent roomView;
    JComponent roomSelectionView;
    JComponent presetView;
    FrameView mainFrame;
    Device device;

    public DigiHdmiApp() {
        this.currentView = null;
        this.homeView = null;
        this.roomView = null;
        this.roomSelectionView = null;
        this.presetView = null;
        this.mainFrame = null;

        this.device = null;
    }

    public JComponent getCurrentView() {
        return this.currentView;
    }

    public void setCurrentView(JComponent currentView) {
        this.currentView = currentView;
    }

    public Device getDevice() {
        return this.device;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        this.device = new Device();

        /*if (!getContext().getResourceMap().getString("connectOnStart", new Object[0]).isEmpty()) {
            try {
                this.device.connect();
            } catch (IOException ex) {
                int choice = JOptionPane.showConfirmDialog(null, "An error occured during startup, would you like to continue in off-line mode?\nError: " + ex.getMessage(), "Error during startup", 0);

                if (choice == 1) {
                    System.exit(1);
                }
            }
        }*/

        this.mainFrame = new DigiHdmiAppMainView(this);
        initializeComponents();

        show(this.mainFrame);
        showHomeView();
        this.mainFrame.getFrame().setMinimumSize(new Dimension(600, 400));
        this.mainFrame.getFrame().setPreferredSize(new Dimension(600, 400));
        this.mainFrame.getFrame().setLocationRelativeTo(null);
    }

    protected void ready() {
        super.ready();
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of DigiHdmiApp
     */
    public static DigiHdmiApp getApplication() {
        return (DigiHdmiApp) Application.getInstance(DigiHdmiApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(DigiHdmiApp.class, args);
    }

    private void initializeComponents() {
        this.roomView = new RoomView();
        this.roomSelectionView = new RoomSelectionView();
        this.presetView = new PresetLoadListView();
        this.homeView = new HomePanel();

        ActionMap menuActionMap = getContext().getActionMap(new MenuActions());
        ((DigiHdmiAppMainView) this.mainFrame).setConnectMenuItemAction(menuActionMap.get("toggleDeviceConnect"));
        ((DigiHdmiAppMainView) this.mainFrame).setDeviceMenuAction(menuActionMap.get("menuDevice"));
    }

    @org.jdesktop.application.Action
    public void showHomeView() {
        showPanel(this.homeView, "Home");
    }

    @org.jdesktop.application.Action
    public void showRoomView() {
        ((ButtonListView) this.roomView).getButtonsPanel().clear();
        showPanel(this.roomView, "Room View", new ConnectorActions(), "showOutputList");
    }

    @org.jdesktop.application.Action
    public void showRoomSelectionView() {
        ((ButtonListView) this.roomSelectionView).getButtonsPanel().clear();
        showPanel(this.roomSelectionView, "Inputs View");
    }

    @org.jdesktop.application.Action
    public void showMatrixView() {
        JOptionPane.showMessageDialog(null, "Ah ah ah! Not so fast! This isn't implemented yet.");
    }

    @org.jdesktop.application.Action
    public void showPresetListView() {
        ((ButtonListView) this.presetView).getButtonsPanel().clear();
        showPanel(this.presetView, "Preset View", new PresetActions(), "showPresetListForLoad");
    }

    private void showPanel(JComponent panel, String title) {
        this.currentView = panel;
        this.mainFrame.setComponent(this.currentView);
        this.mainFrame.getFrame().setTitle(title);

        this.mainFrame.getFrame().setVisible(true);
        this.mainFrame.getFrame().repaint();
    }

    private void showPanel(JComponent panel, String title, Object actionsInstance, String actionName) {
        showPanel(panel, title);

        ActionMap m = getContext().getActionMap(actionsInstance);
        javax.swing.Action getoutputs = m.get(actionName);
        getoutputs.actionPerformed(new ActionEvent(this, 1001, ""));
    }

    @org.jdesktop.application.Action
    public void lockApp() {
        JOptionPane.showMessageDialog(null, "Ah ah ah! Not so fast! This isn't implemented yet.");
    }

    @org.jdesktop.application.Action
    public void showUtilView() {
        JOptionPane.showMessageDialog(null, "Ah ah ah! Not so fast! This isn't implemented yet.");
    }
}
