package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.actions.ConnectorActions;
import com.intelix.digihdmi.model.Connector;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import org.jdesktop.application.Application;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class GetInputsForSelectionTask extends GetConnectorsTask {

    public GetInputsForSelectionTask(Application app) {
        super(app);
        numConnectors = device.getNumInputs();
    }

    @Override
    protected Action getConnectorAction(Connector c) {
        ActionMap m = getContext().getActionMap(new ConnectorActions());
        Action getInputs = m.get("connectInputAndOutput");

        return getInputs;
    }

    @Override
    protected Object doInBackground() throws Exception {
        Object obj = super.doInBackground();

        Connector c = ((DigiHdmiApp) Application.getInstance()).getDevice().getInputForSelectedOutput();
        if (c != null) {
            int buttonNumber = c.getIndex() - 1;   // buttons are zero - based, connectors are 1-based
            AbstractButton b = panel.getButton(buttonNumber);
            b.setSelected(true);
            device.setSelectedInput(c.getIndex());
        }

        return obj;
    }
}
