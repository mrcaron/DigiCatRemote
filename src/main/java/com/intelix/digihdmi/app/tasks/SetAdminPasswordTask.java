/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Device;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author mcaron
 */
public class SetAdminPasswordTask extends Task {

    String pwd;
    Device device;

    public SetAdminPasswordTask(Application app, String passwd) {
        super(app);
        pwd = passwd;
        device = ((DigiHdmiApp)app).getDevice();
    }

    @Override
    protected Object doInBackground() throws Exception {
        if (pwd == null) {
            message("badpass");
        } else {
            message("settingPassword");
            device.setAdminPassword(pwd);
            message("passwordSet");
        }
        return null;
    }

}
