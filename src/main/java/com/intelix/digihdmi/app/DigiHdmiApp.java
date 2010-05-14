/*
 * DigiHdmiApp.java
 */
package com.intelix.digihdmi.app;

import com.intelix.digihdmi.app.actions.ConnectorActions;
import com.intelix.digihdmi.app.actions.MatrixActions;
import com.intelix.digihdmi.app.actions.MenuActions;
import com.intelix.digihdmi.app.actions.PresetActions;
import com.intelix.digihdmi.app.views.PresetLoadListView;
import com.intelix.digihdmi.app.views.RoomSelectionView;
import com.intelix.digihdmi.app.views.DigiHdmiAppMainView;
import com.intelix.digihdmi.app.views.ButtonListView;
import com.intelix.digihdmi.app.views.HomePanel;
import com.intelix.digihdmi.app.views.LockView;
import com.intelix.digihdmi.app.views.MatrixView;
import com.intelix.digihdmi.app.views.RoomView;
import com.intelix.digihdmi.model.Device;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;
import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;
import org.jdesktop.application.Task.InputBlocker;

/**
 * The main class of the application.
 */
public class DigiHdmiApp extends SingleFrameApplication {

    JComponent currentView;
    JComponent homeView;
    JComponent roomView;
    JComponent roomSelectionView;
    JComponent presetView;
    JComponent matrixView;
    JComponent lockView;
    FrameView mainFrame;
    Device device;

    public DigiHdmiApp() {
        currentView = null;
        homeView = null;
        roomView = null;
        roomSelectionView = null;
        presetView = null;
        mainFrame = null;

        device = null;
    }

    public JComponent getCurrentView() {
        return currentView;
    }

    public void setCurrentView(JComponent currentView) {
        this.currentView = currentView;
    }

    public Device getDevice() {
        return device;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        device = new Device();

        /*if (!getContext().getResourceMap().getString("connectOnStart", new Object[0]).isEmpty()) {
            try {
                device.connect();
            } catch (IOException ex) {
                int choice = JOptionPane.showConfirmDialog(null, "An error occured during startup, would you like to continue in off-line mode?\nError: " + ex.getMessage(), "Error during startup", 0);

                if (choice == 1) {
                    System.exit(1);
                }
            }
        }*/

        mainFrame = new DigiHdmiAppMainView(this);
        initializeComponents();

        show(mainFrame);
        showHomeView();
        mainFrame.getFrame().setMinimumSize(new Dimension(700, 400));
        mainFrame.getFrame().setPreferredSize(new Dimension(700, 400));
        mainFrame.getFrame().setLocationRelativeTo(null);
    }

    @Override
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
        roomView = new RoomView();
        roomSelectionView = new RoomSelectionView();
        presetView = new PresetLoadListView();
        homeView = new HomePanel();
        //int numOuts = getDevice().getNumOutputs();

        lockView = new LockView();
        ((LockView)lockView).setUnlockAction(getContext().getActionMap().get("showHomeView"));

        matrixView = new MatrixView();
        ((MatrixView)matrixView).getMatrixPanel().setDefaultButtonAction(
            getContext().getActionMap(new MatrixActions()).get("setConnection")
        );

        ActionMap menuActionMap = getContext().getActionMap(new MenuActions());
        ((DigiHdmiAppMainView) mainFrame).setConnectMenuItemAction(menuActionMap.get("toggleDeviceConnect"));
        ((DigiHdmiAppMainView) mainFrame).setDeviceMenuAction(menuActionMap.get("menuDevice"));
        ((DigiHdmiAppMainView) mainFrame).setResetCacheMenuItemAction(menuActionMap.get("resetCache"));
        
    }

    @org.jdesktop.application.Action
    public void showHomeView() {
        showPanel(homeView, "Home");
    }

    @org.jdesktop.application.Action
    public void showRoomView() {
        ((ButtonListView) roomView).getButtonsPanel().clear();
        showPanel(roomView, "Room View", new ConnectorActions(), "showOutputList");
    }

    @org.jdesktop.application.Action
    public void showRoomSelectionView() {
        ((ButtonListView) roomSelectionView).getButtonsPanel().clear();
        showPanel(roomSelectionView, "Inputs View");
    }

    @org.jdesktop.application.Action
    public void showMatrixView() {
        showPanel(matrixView, "Matrix View", new MatrixActions(), "loadMatrix");
        //JOptionPane.showMessageDialog(null, "Ah ah ah! Not so fast! This isn't implemented yet.");
    }

    @org.jdesktop.application.Action
    public void showPresetListView() {
        ((ButtonListView) presetView).getButtonsPanel().clear();
        showPanel(presetView, "Preset View", new PresetActions(), "showPresetListForLoad");
    }

    @org.jdesktop.application.Action
    public void showPresetSaveView() {
        ((ButtonListView) presetView).getButtonsPanel().clear();
        showPanel(presetView, "Preset View", new PresetActions(), "showPresetListForSave");
    }

    private void showPanel(JComponent panel, String title) {
        currentView = panel;
        mainFrame.setComponent(currentView);
        mainFrame.getFrame().setTitle(title);

        mainFrame.getFrame().setVisible(true);
        mainFrame.getFrame().repaint();
    }

    private void showPanel(JComponent panel, String title, Object actionsInstance, String actionName) {
        showPanel(panel, title);

        ActionMap m = getContext().getActionMap(actionsInstance);
        javax.swing.Action getoutputs = m.get(actionName);
        getoutputs.actionPerformed(new ActionEvent(this, 1001, ""));
    }

    @org.jdesktop.application.Action
    public void lockApp() {
        //JOptionPane.showMessageDialog(null, "Ah ah ah! Not so fast! This isn't implemented yet.");

        // TODO: add a confirmation dialog
        showPanel(lockView, "Lock View");
    }

    @org.jdesktop.application.Action
    public void showUtilView() {
        JOptionPane.showMessageDialog(null, "Ah ah ah! Not so fast! This isn't implemented yet.");
    }

    public class BusyInputBlocker extends InputBlocker
    {
        public BusyInputBlocker(Task t) {
            super(t, Task.BlockingScope.WINDOW,mainFrame.getFrame().getGlassPane());
            mainFrame.getFrame().getGlassPane().addMouseListener(new MouseInputAdapter() {});
        }

        @Override
        protected void block() {
            getMainFrame().getGlassPane().setVisible(true);
            getMainFrame().getGlassPane().requestFocusInWindow();
        }

        @Override
        protected void unblock() {
            getMainFrame().getGlassPane().setVisible(false);
        }
    }
}
