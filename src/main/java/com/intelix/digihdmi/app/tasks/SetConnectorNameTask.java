package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Connector;
import com.intelix.digihdmi.model.Device;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class SetConnectorNameTask extends Task {

    DigiHdmiApp digiApp;
    Device device;
    String newName;
    Connector selected;

    public SetConnectorNameTask(Application app, String newName, Connector c) {
        super(app);
        digiApp = (DigiHdmiApp)app;
        device = digiApp.getDevice();

        this.selected = c;
        this.newName = newName;
    }

    @Override
    protected Object doInBackground() throws Exception {
        selected.setName(newName);
        return null;
    }

}
