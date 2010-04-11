package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.actions.ConnectorActions;
import com.intelix.digihdmi.model.Connector;
import java.util.Enumeration;
import javax.swing.Action;
import javax.swing.ActionMap;
import org.jdesktop.application.Application;

public class GetOutputsTask extends GetConnectorsTask {

    public GetOutputsTask(Application app) {
        super(app);
    }

    @Override
    protected Enumeration<Connector> getConnectors() {
        return device.getOutputs();
    }

    @Override
    protected Action getConnectorAction(Connector c) {
        ActionMap m = getContext().getActionMap(new ConnectorActions());
        Action getInputs = m.get("showInputListForSelection");

        return getInputs;
    }
}
