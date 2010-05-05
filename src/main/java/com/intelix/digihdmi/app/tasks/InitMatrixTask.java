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

    public InitMatrixTask(Application app) {
        super(app);
        device = ((DigiHdmiApp) app).getDevice();
    }

    @Override
    protected Object doInBackground() throws Exception {
        DigiHdmiApp dApp = (DigiHdmiApp)getApplication();
        MatrixView view = (MatrixView) dApp.getCurrentView();
        MatrixPanel panel = view.getMatrixPanel();

        // Set the input names
        Enumeration<Connector> inputs = device.getInputs();
        for (int i=0;inputs.hasMoreElements();i++)
        {
            panel.setInputName(i,inputs.nextElement().getName());
        }
        // Set the output names
        Enumeration<Connector> outputs = device.getOutputs();
        for (int i=0;outputs.hasMoreElements();i++)
        {
            panel.setOutputName(i,outputs.nextElement().getName());
        }
        // Get the connections
        HashMap<Integer,Integer> xp = device.getCrossPoints();
        Iterator<Entry<Integer,Integer>> xpIterator = xp.entrySet().iterator();
        while(xpIterator.hasNext())
        {
            Entry<Integer,Integer> e = xpIterator.next();
            panel.select(e.getKey() /* Output */, e.getValue() /* Input */);
        }

        return null;

    }

    private Object MatrixView(DigiHdmiApp digiHdmiApp) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
