package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.ButtonContainerPanel;
import com.intelix.digihdmi.app.views.ButtonListView;
import com.intelix.digihdmi.model.Connector;
import com.intelix.digihdmi.model.Device;
import com.intelix.digihdmi.util.BasicAction;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public abstract class GetConnectorsTask extends Task<Void,GetConnectorsTask.Chunk> /*implements PropertyChangeListener*/ {

    protected final ButtonContainerPanel panel;
    protected final Device device;
    protected int numConnectors;
    private Logger logger;

    public GetConnectorsTask(Application app) {
        super(app);

        logger = Logger.getLogger(getClass().getCanonicalName());

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
    protected Void doInBackground()
            throws Exception {
        if ((panel != null) && (device != null)) {
            Enumeration connectorList = getConnectors();
            for (int i=0; connectorList.hasMoreElements(); i++) {
                message("loadingConnectionD",i+1);
                Connector c = (Connector) connectorList.nextElement();
                postProcessConnector(c);
                message("loadedConnectionDS",i,c.getName());
                setProgress(i,0,numConnectors);
                logger.info("Loaded connection " + (i+1));
            }
        }
        return null;
    }

    @Override
    protected void succeeded(Void result) {
        message("finished");
    }

    protected void postProcessConnector(Connector c) {
        logger.info("Creating button with name" + c.getName());
        publish(new Chunk( c.getName(), c.getIcon() , getConnectorAction(c) ));
    }

    private static NumberFormat f = null;

    @Override
    protected void process(List<Chunk> values) {
        if (f == null)
        {
            f = NumberFormat.getInstance();
            f.setMinimumIntegerDigits(2);
        }
        for(Chunk c : values)
            panel.addButton(c.getName(), c.getIcon() > 0 ? 
                    (getIconNameHead() + "_" + f.format(c.getIcon()))
                  : "", c.getAction());
    }

    protected abstract String getIconNameHead();

    /*
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("progress"))
        {
            setProgress((Float)evt.getNewValue());
        }
    }*/

    protected class Chunk {
        String name;
        int icon;
        Action action;
        public Chunk(String name, int icon, Action action) {
            this.name = name;
            this.icon = icon;
            this.action = action;
        }

        public String getName() {
            return name;
        }

        public Action getAction() {
            return action;
        }

        public int getIcon() {
            return icon;
        }
    }
}