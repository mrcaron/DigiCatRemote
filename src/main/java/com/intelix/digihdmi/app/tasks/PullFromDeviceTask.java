package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Device;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class PullFromDeviceTask extends Task implements PropertyChangeListener {

    DigiHdmiApp app;
    Device device;

    public PullFromDeviceTask(Application app) {
        super(app);
        device = ((DigiHdmiApp)app).getDevice();
        device.addPropertyChangeListener(this);
    }

    @Override
    protected Object doInBackground() throws Exception {
        //device.pull();
        return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (! evt.getPropertyName().equals("progress"))
            return;

        setProgress((Float)evt.getNewValue());
    }
}
