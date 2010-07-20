package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.actions.ConnectorActions;
import com.intelix.digihdmi.app.views.ButtonContainerPanel;
import com.intelix.digihdmi.model.Connector;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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
    protected Enumeration<Connector> getConnectors() {
        return device.getInputs();
    }

    @Override
    protected Action getConnectorAction(Connector c) {
        ActionMap m = getContext().getActionMap(new ConnectorActions());
        Action getInputs = m.get("connectInputAndOutput");

        return getInputs;
    }

    @Override
    protected Void doInBackground() throws Exception {
        super.doInBackground();

        Connector c = ((DigiHdmiApp) Application.getInstance()).getDevice().getInputForSelectedOutput();
        if (c != null) {
            final ButtonContainerPanel panel = this.panel;
            final int buttonNumber = c.getIndex() - 1;   // buttons are zero - based, connectors are 1-based

            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    AbstractButton b = panel.getButton(buttonNumber);
                    b.setSelected(true);
                }
            });

            device.setSelectedInput(c.getIndex() - 1);

        }

        return null;
    }
}
