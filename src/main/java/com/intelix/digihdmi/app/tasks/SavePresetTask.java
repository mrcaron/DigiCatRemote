package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Device;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class SavePresetTask extends Task {

    private Device device;

    public SavePresetTask(Application app) {
        super(app);
        device = ((DigiHdmiApp)app).getDevice();
    }

    @Override
    protected Object doInBackground()
    throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
