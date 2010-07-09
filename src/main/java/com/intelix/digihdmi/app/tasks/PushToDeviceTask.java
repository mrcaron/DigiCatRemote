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
    protected Object doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
