package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.MatrixPanel;
import com.intelix.digihdmi.app.views.MatrixView;
import com.intelix.digihdmi.model.Device;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class ApplyPresetTask extends Task {

    int index = 0;
    Device device = null;

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

        DigiHdmiApp dApp = (DigiHdmiApp)getApplication();
        MatrixView view = (MatrixView) dApp.getCurrentView();
        MatrixPanel panel = view.getMatrixPanel();
        HashMap<Integer,Integer> xp = device.getCrossPoints();
        Iterator<Entry<Integer,Integer>> xpIterator = xp.entrySet().iterator();
        for(int i=0; xpIterator.hasNext(); i++)
        {
            setMessage("Setting output " + (i+1));
            Entry<Integer,Integer> e = xpIterator.next();
            panel.select(e.getKey()-1 /* Output */, e.getValue()-1 /* Input */, true /*INIT*/);
        }
        

        return null;
    }
}
