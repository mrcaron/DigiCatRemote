package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.MatrixView;
import com.intelix.digihdmi.model.Connector;
import com.intelix.digihdmi.model.Device;
import java.util.Enumeration;
import javax.swing.JComponent;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class ApplyPresetTask extends Task {

    int index = 0;
    Device device = null;
    //MatrixView matrix = null;

    public ApplyPresetTask(Application app, int index) {
        super(app);
        this.index = index;
        device = ((DigiHdmiApp)app).getDevice();
    }

    @Override
    protected Object doInBackground() throws Exception {
        // Tell the device to load the preset(index)
        message("loadingPreset");
        device.loadPreset(index);
        message("presetLoaded");
        // Update matrix view when we're done
        Enumeration<Connector> outputs = device.getOutputs(false /* not live */);
        while(outputs.hasMoreElements())
        {
            Connector output = outputs.nextElement();
            setMessage("Setting output " + output.getIndex());
            device.setSelectedOutput(output.getIndex());
            Connector input = device.getInputForSelectedOutput(false /* not live */);
            JComponent c = ((DigiHdmiApp)getApplication()).getCurrentView();
            if (c instanceof MatrixView)
            {
                ((MatrixView)c).getMatrixPanel().select(output.getIndex(), input.getIndex(), true);
            }
        }

        return null;
    }
}
