package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Connector;
import com.intelix.digihdmi.model.Device;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class SetConnectorIconTask extends Task {

    DigiHdmiApp digiApp;
    Device device;
    int iconId;
    Connector selected;

    public SetConnectorIconTask(Application app, int iconId, Connector c) {
        super(app);
        digiApp = (DigiHdmiApp)app;
        device = digiApp.getDevice();

        this.selected = c;
        this.iconId = iconId;
    }

    @Override
    protected Object doInBackground() throws Exception {
        selected.setIcon(iconId);
        return null;
    }

}
