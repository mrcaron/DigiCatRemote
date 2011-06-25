/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.MatrixPanel;
import com.intelix.digihdmi.app.views.MatrixView;
import com.intelix.digihdmi.model.Connector;
import com.intelix.digihdmi.model.Device;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author mcaron
 */
public class InitMatrixTask extends Task {

    protected final Device device;
    boolean loadNames;

    public InitMatrixTask(Application app) {
        this(app, true);
    }

    public InitMatrixTask(Application app, boolean loadNames) {
        super(app);
        device = ((DigiHdmiApp) app).getDevice();
        this.loadNames = loadNames;
    }

    @Override
    protected Object doInBackground() throws Exception {
        DigiHdmiApp dApp = (DigiHdmiApp)getApplication();
        MatrixView view = (MatrixView) dApp.getCurrentView();
        MatrixPanel panel = view.getMatrixPanel();

        // TODO (6/25): At somepoint, we get into an xp.size = 9. How that happens, I don't know.
        HashMap<Integer,Integer> xp = device.getCrossPoints();
        int numCrossPoints = xp.size();
        int numOutputs = device.getNumOutputs();
        int numInputs = device.getNumInputs();
        int totalTasks = numCrossPoints + numInputs + numOutputs;

        // Get the connections
        message("loadingConnections");
        Iterator<Entry<Integer,Integer>> xpIterator = xp.entrySet().iterator();
        for(int i=0;xpIterator.hasNext();i++)
        {
            Entry<Integer,Integer> e = xpIterator.next();
            panel.select(e.getKey() /* Output */, e.getValue() /* Input */, true /*INIT*/);
            setProgress(i, 0, totalTasks);
        }

        if (loadNames)
        {
            // Set the input names
            message("loadingInputNames");
            Enumeration<Connector> inputs = device.getInputs();
            for (int i=0;inputs.hasMoreElements();i++)
            {
                message("loadingInputNameX",i);
                panel.setInputName(i,inputs.nextElement().getName());
                setProgress(i+numCrossPoints, 0, totalTasks);
            }
            // Set the output names
            Enumeration<Connector> outputs = device.getOutputs();
            message("loadingOutputNames");
            for (int i=0;outputs.hasMoreElements();i++)
            {
                message("loadingOutputNameX",i);
                panel.setOutputName(i,outputs.nextElement().getName());
                setProgress(i+numCrossPoints+numInputs, 0, totalTasks);
            }
        }
        
        message("loadingFinished");
        return null;
    }
}
