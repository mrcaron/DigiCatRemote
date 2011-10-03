package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Connector;
import com.intelix.digihdmi.model.Device;
import com.intelix.digihdmi.model.Preset;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class PullFromDeviceTask extends Task<Void,Void> implements PropertyChangeListener {

    DigiHdmiApp app;
    Device device;

    public PullFromDeviceTask(Application app) {
        super(app);
        device = ((DigiHdmiApp)app).getDevice();
        //device.addPropertyChangeListener(this);
        device.setPulling(true);
    }

    @Override
    protected Void doInBackground() throws Exception {
        //device.pull();

        message("downloadingInputs");
        float total = device.getNumInputs();
        Enumeration<Connector> ins = device.getInputs(true /* LIVE */);
        for(int i=0; ins.hasMoreElements(); i++)
        {
            ins.nextElement();
            setProgress((i+1)/total);
            Thread.sleep(100);
        }

        message("downloadingOutputs");
        total = device.getNumOutputs();
        Enumeration<Connector> outs = device.getOutputs(true /* LIVE */);
        for(int i=0; outs.hasMoreElements(); i++)
        {
            outs.nextElement();
            setProgress((i+1)/total);
            Thread.sleep(100);
        }

        message("downloadingPresets");
        total = device.getNumPresets();
        Enumeration<Preset> presets = device.getPresets(true /* LIVE */, true /* FETCH CXN DATA */);
        for(int i=0; presets.hasMoreElements(); i++)
        {
            presets.nextElement();
            setProgress((i+1) / total);
            Thread.sleep(100);
        }

        return null;
    }

    @Override
    protected void finished() {
        super.finished();
        device.setPulling(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (! evt.getPropertyName().equals("progress"))
            return;

        setProgress((Float)evt.getNewValue());
    }
}
