package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Device;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.ActionMap;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class MenuActions {

    ActionMap actionMap = null;

    public MenuActions() {
        actionMap = Application.getInstance().getContext().getActionMap(this);
    }

    @Action
    public Task toggleDeviceConnect(ActionEvent av) {
        JCheckBoxMenuItem item = (JCheckBoxMenuItem) av.getSource();
        return new ToggleDeviceConnectTask(Application.getInstance(), item);
    }

    @Action
    public void menuDevice() {
    }

    @Action
    public Task resetCache() {
        return new ResetCacheTask(Application.getInstance());
    }

    @Action
    public void onDeviceSettings() {
        ((DigiHdmiApp)Application.getInstance()).showOptionsDlg();
    }

    private class ResetCacheTask extends Task {
        ResetCacheTask(Application app) {super(app);}
        @Override
        protected Object doInBackground() throws Exception {
            ((DigiHdmiApp)getApplication()).getDevice().setFullReset(true);
            setMessage("Cache Reset.");
            return null;
        }
    }

    private class ToggleDeviceConnectTask extends Task<Object, Void> {

        JCheckBoxMenuItem item;
        Device device;

        ToggleDeviceConnectTask(Application app, JCheckBoxMenuItem item) {
            super(app);
            this.item = item;
            this.device = ((DigiHdmiApp) Application.getInstance()).getDevice();
        }

        @Override
        protected Object doInBackground() {
            try {
                if (this.item.isSelected()) {
                    this.device.connect();
                } else {
                    this.device.disconnect();
                }
                return (this.item.isSelected()) ? "Connected!" : "Disconnected!";
            } catch (IOException ex) {
                this.item.setSelected(false);
                System.err.println("Error with connection: " + ex.getMessage());
                return "Error: " + ex.getMessage();
            }
        }

        @Override
        protected void succeeded(Object result) {
            setMessage((String) result);
        }

        @Override
        protected void failed(Throwable cause) {
            super.failed(cause);
            this.item.setSelected(false);
            setMessage("Failed to connect.");
        }
    }

    @Action
    public void onFileSave()
    {
        JOptionPane.showMessageDialog(null, "File::Save");
    }

    @Action
    public void onFileLoad()
    {
        JOptionPane.showMessageDialog(null, "File::Load");
    }
}