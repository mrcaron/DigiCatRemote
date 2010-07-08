package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.DeviceConnectionDlg;
import com.intelix.digihdmi.model.Device;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class ConnectionDialogActions {
    Device device;
    DigiHdmiApp app;
    DeviceConnectionDlg dlg;

    public ConnectionDialogActions() {
        app = (DigiHdmiApp)Application.getInstance();
        device = app.getDevice();
        dlg = app.getConnectionDlg();
    }

    @Action
    public void onOk()
    {
        dlg.setVisible(false);
    }

    @Action
    public void onCancel()
    {
        dlg.setVisible(false);
    }

    @Action
    public void onTest()
    {

    }

    @Action
    public void onConnect()
    {
        
    }
}
