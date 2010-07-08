package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.DevicePrefsDlg;
import com.intelix.digihdmi.model.Device;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class OptionsDialogActions {

    DigiHdmiApp app;
    Device device;
    DevicePrefsDlg dlg;

    public OptionsDialogActions() {
        app = (DigiHdmiApp)Application.getInstance();
        device = app.getDevice();
        dlg = app.getOptionsDlg();
    }

    @Action
    public void onCancel()
    {
        dlg.setVisible(false);
    }

    @Action
    public void onOk()
    {
        alterDevice();
        dlg.setVisible(false);
    }

    @Action
    public void onConnectProps()
    {
        app.showConnectionDlg();
    }

    protected void alterDevice()
    {
        // Dlg information
        int dlgNI = dlg.getNumInputs();
        int dlgNO = dlg.getNumOutputs();
        int dlgNP = dlg.getNumPresets();
        int dlgLPL = dlg.getLockPassLength();
        int dlgAPL = dlg.getAdminPassLength();
        int dlgPNL = dlg.getPresetNameLength();

        if (device.getNumInputs() != dlgNI)
            device.setNumInputs(dlgNI);

        if (device.getNumOutputs() != dlgNO)
            device.setNumOutputs(dlgNO);

        if (device.getNumPresets() != dlgNP)
            device.setNumPresets(dlgNP);

        if (device.getLockPassLength() != dlgLPL)
            device.setLockPassLength(dlgLPL);

        if (device.getAdminPassLength() != dlgAPL)
            device.setAdminPassLength(dlgAPL);

        if (device.getPresetNameLength() != dlgPNL)
            device.setPresetNameLength(dlgPNL);
    }
}
