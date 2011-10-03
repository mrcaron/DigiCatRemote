package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.actions.ConnectorActions;
import com.intelix.digihdmi.model.Connector;
import java.util.Enumeration;
import javax.swing.Action;
import javax.swing.ActionMap;
import org.jdesktop.application.Application;

public class GetOutputsForCustomizationTask extends GetConnectorsTask {

    public GetOutputsForCustomizationTask(Application app) {
        super(app);
        numConnectors = device.getNumOutputs();
    }

    @Override
    protected Enumeration<Connector> getConnectors() {
        return device.getOutputs();
    }

    @Override
    protected Action getConnectorAction(Connector c) {
        ActionMap m = getContext().getActionMap(new ConnectorActions());
        Action getOutputs = m.get("changeOutput");

        return getOutputs;
    }

    @Override
    protected String getIconNameHead() {
        return "output";
    }
}
