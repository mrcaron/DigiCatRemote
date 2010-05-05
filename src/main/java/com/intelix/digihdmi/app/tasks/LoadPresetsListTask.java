package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.ButtonContainerPanel;
import com.intelix.digihdmi.app.views.ButtonListView;
import com.intelix.digihdmi.app.actions.PresetActions;
import com.intelix.digihdmi.model.Device;
import com.intelix.digihdmi.model.Preset;
import java.util.Enumeration;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class LoadPresetsListTask extends Task {

    protected final Device device;
    protected final ButtonContainerPanel panel;

    public LoadPresetsListTask(Application app) {
        super(app);
        this.device = ((DigiHdmiApp) app).getDevice();

        JComponent c = ((DigiHdmiApp) app).getCurrentView();
        if (c instanceof ButtonListView) {
            this.panel = ((ButtonListView) c).getButtonsPanel();
        } else {
            this.panel = null;
        }
    }

    @Override
    protected Object doInBackground()
            throws Exception {
        ActionMap map = getContext().getActionMap(new PresetActions());

        if ((this.panel != null) && (this.device != null)) {
            Enumeration presetList = this.device.getPresets();
            while (presetList.hasMoreElements()) {
                Preset c = (Preset) presetList.nextElement();
                setMessage("Working... [" + c.getName() + "]");
                this.panel.addButton(c.getName(), null, map.get("applyPresetAndShowMatrixView"));
            }
        }
        return null;
    }
}
