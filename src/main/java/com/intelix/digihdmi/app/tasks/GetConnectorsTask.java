package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.ButtonContainerPanel;
import com.intelix.digihdmi.app.views.ButtonListView;
import com.intelix.digihdmi.model.Connector;
import com.intelix.digihdmi.model.Device;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class GetConnectorsTask extends Task {

    protected final ButtonContainerPanel panel;
    protected final Device device;

    public GetConnectorsTask(Application app) {
        super(app);

        device = ((DigiHdmiApp) app).getDevice();

        JComponent c = ((DigiHdmiApp) app).getCurrentView();
        if (c instanceof ButtonListView) {
            panel = ((ButtonListView) c).getButtonsPanel();
        } else {
            panel = null;
        }
    }

    protected Enumeration<Connector> getConnectors() {
        return device.getOutputs();
    }

    protected Action getConnectorAction(Connector c) {
        return new BasicAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello World!");
            }
        };
    }

    @Override
    protected Object doInBackground()
            throws Exception {
        if ((panel != null) && (device != null)) {
            Enumeration connectorList = getConnectors();
            while (connectorList.hasMoreElements()) {
                Connector c = (Connector) connectorList.nextElement();
                setMessage("Working... [" + c.getName() + "]");
                panel.addButton(c.getName(), c.getIcon(), getConnectorAction(c));
            }
        }
        return null;
    }

    @Override
    protected void succeeded(Object result) {
        setMessage("Done.");
    }

    protected abstract class BasicAction implements Action {

        BasicAction() {
        }

        @Override
        public Object getValue(String key) {
            return null;
        }

        @Override
        public void putValue(String key, Object value) {
        }

        @Override
        public void setEnabled(boolean b) {
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {
        }
    }
}