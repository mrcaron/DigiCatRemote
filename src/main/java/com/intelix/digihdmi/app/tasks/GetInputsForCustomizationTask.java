package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.actions.ConnectorActions;
import com.intelix.digihdmi.model.Connector;
import java.util.Enumeration;
import javax.swing.Action;
import javax.swing.ActionMap;
import org.jdesktop.application.Application;

public class GetInputsForCustomizationTask extends GetConnectorsTask {

    public GetInputsForCustomizationTask(Application app) {
        super(app);
        numConnectors = device.getNumInputs();
    }

    @Override
    protected Enumeration<Connector> getConnectors() {
        return device.getInputs();
    }

    @Override
    protected Action getConnectorAction(Connector c) {
        ActionMap m = getContext().getActionMap(new ConnectorActions());
        Action getInputs = m.get("changeInput");

        return getInputs;
    }
}
