package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Device;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class SavePresetTask extends Task {

    private Device device;
    int index;
    String name;

    public SavePresetTask(Application app, int index, String name) {
        super(app);
        device = ((DigiHdmiApp)app).getDevice();
        this.index = index;
        this.name = name;
    }

    @Override
    protected Object doInBackground()
    throws Exception {
        // Tell the device to save the preset(index)
        message("savingPreset");
        device.savePreset(index, name);
        message("presetSaved");

        // No update to matrix necessary on a save!
        return null;
    }
}
