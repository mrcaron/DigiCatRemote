package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Device;
import com.intelix.digihdmi.util.Selectable;
import java.io.IOException;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class ToggleDeviceConnectTask extends Task<Object, Void> {

    Selectable item;
    Device device;

    public ToggleDeviceConnectTask(Application app, Selectable item) {
        super(app);
        this.item = item;
        this.device = ((DigiHdmiApp) Application.getInstance()).getDevice();
    }

    @Override
    protected Object doInBackground() {
        try {
            if (this.item.isSelected()) {
                this.device.connect();
                ((DigiHdmiApp)getApplication()).showSyncDlg();
            } else {
                this.device.disconnect();
            }
            return (this.item.isSelected()) ? "Connected!" : "Disconnected!";
        } catch (IOException ex) {
            item.setSelected(false);
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