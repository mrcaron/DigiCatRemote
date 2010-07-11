package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.ButtonContainerPanel;
import com.intelix.digihdmi.app.views.ButtonListView;
import com.intelix.digihdmi.model.Connector;
import com.intelix.digihdmi.model.Device;
import com.intelix.digihdmi.util.BasicAction;
import java.awt.event.ActionEvent;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class GetConnectorsTask extends Task /*implements PropertyChangeListener*/ {

    protected final ButtonContainerPanel panel;
    protected final Device device;
    protected int numConnectors;

    public GetConnectorsTask(Application app) {
        super(app);

        device = ((DigiHdmiApp) app).getDevice();
        //device.addPropertyChangeListener(this);

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
            for (int i=0; connectorList.hasMoreElements(); i++) {
                message("loadingConnectionD",i);
                Connector c = (Connector) connectorList.nextElement();
                message("loadedConnectionDS",i,c.getName());
                setProgress(i,0,numConnectors);
                postProcessConnector(c);
            }
        }
        return null;
    }

    @Override
    protected void succeeded(Object result) {
        message("finished");
    }

    protected void postProcessConnector(Connector c) {
        panel.addButton(c.getName(), c.getIcon(), getConnectorAction(c));
    }

    /*
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("progress"))
        {
            setProgress((Float)evt.getNewValue());
        }
    }*/
}