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
public class PushToDeviceTask extends Task<Void,Void> implements PropertyChangeListener {

    DigiHdmiApp app;
    Device device;

    public PushToDeviceTask(Application app) {
        super(app);
        device = ((DigiHdmiApp)app).getDevice();
        device.setPushing(true);
    }

    @Override
    /**
     * Assumes we're connected to the device
     * Tasks to accomplish:<ul>
     *   <li>Push all IO Names and Icons to device</li>
     *   <li>Push all Preset Names and configurations to device</li>
     * </ul>
     */
    protected Void doInBackground() throws Exception {
        message("uploadingInputs");
        float total = device.getNumInputs();
        Enumeration<Connector> ins = device.getInputs(false /* NOT LIVE */);
        for(int i=0; ins.hasMoreElements(); i++)
        {
            device.push(ins.nextElement());
            setProgress((i+1) / total);
            Thread.sleep(100);
        }

        message("uploadingOutputs");
        total = device.getNumInputs();
        Enumeration<Connector> outs = device.getOutputs(false /* NOT LIVE */);
        for(int i=0; outs.hasMoreElements(); i++)
        {
            device.push(outs.nextElement());
            setProgress((i+1) / total);
            Thread.sleep(100);
        }
        
        message("uploadingPresets");
        total = device.getNumPresets();
        Enumeration<Preset> presets = device.getPresets(false /* NOT LIVE */);
        for(int i=0; presets.hasMoreElements(); i++)
        {
            presets.nextElement();
            setProgress((i+1) / total);
            Thread.sleep(100);
        }
//        for(int i=0; i<20; i++)
//        {
//            message("loading"+i);
//            setProgress(i/20f);
//            Thread.sleep(500);
//        }
        return null;
    }

    @Override
    protected void finished() {
        super.finished();
        device.setPushing(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (! evt.getPropertyName().equals("progress"))
            return;

        setProgress((Float)evt.getNewValue());
    }

}
