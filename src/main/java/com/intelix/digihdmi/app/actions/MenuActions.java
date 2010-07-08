package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Device;
import com.thoughtworks.xstream.XStream;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import javax.swing.ActionMap;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
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
        ((DigiHdmiApp) Application.getInstance()).showOptionsDlg();
    }

    private class ResetCacheTask extends Task {

        ResetCacheTask(Application app) {
            super(app);
        }

        @Override
        protected Object doInBackground() throws Exception {
            ((DigiHdmiApp) getApplication()).getDevice().setFullReset(true);
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
    public void onFileSave() {
        // Get a file location from the user
        JFileChooser fc = new JFileChooser();
        int result = fc.showSaveDialog(((DigiHdmiApp) Application.getInstance()).getMainFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            // Create the serializer
            XStream xstream = new XStream();
            xstream.autodetectAnnotations(true);

            // setup aliases
            //...

            // get the xml string
            String xml = xstream.toXML(((DigiHdmiApp) Application.getInstance()).getDevice());

            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                if (file.canWrite()) {
                    Writer w = new BufferedWriter(
                            new FileWriter(file));
                    w.write(xml);
                    w.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(((DigiHdmiApp) Application.getInstance()).getMainFrame(),
                        "Can't save " + file.getAbsolutePath() + " to disk.\n\nDetails:\n" + ex.getMessage(),
                        "Fail!", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    @Action
    public void onFileLoad() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(((DigiHdmiApp) Application.getInstance()).getMainFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            XStream xstream = new XStream();
            xstream.autodetectAnnotations(true);
            
            File f = fc.getSelectedFile();
            try {
                Reader r = new FileReader(f);
                Device d = (Device) xstream.fromXML(r);
                ((DigiHdmiApp) Application.getInstance()).setDevice(d);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(((DigiHdmiApp) Application.getInstance()).getMainFrame(),
                        "Can't load " + f.getAbsolutePath() + " from disk.\n\nDetails:\n" + ex.getMessage(),
                        "Fail!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
