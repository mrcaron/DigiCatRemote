package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Device;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class PushToDeviceTask extends Task {

    DigiHdmiApp app;
    Device device;

    public PushToDeviceTask(Application app) {
        super(app);
        device = ((DigiHdmiApp)app).getDevice();
    }

    @Override
    /**
     * Assumes we're connected to the device
     */
    protected Object doInBackground() throws Exception {
        device.push();
        return null;
    }


}
