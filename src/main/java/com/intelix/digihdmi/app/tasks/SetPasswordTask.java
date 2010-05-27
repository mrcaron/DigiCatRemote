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
public abstract class SetPasswordTask extends Task {

    Device device;
    String pwd;

    public SetPasswordTask(Application app) {
        this(app, "");
    }

    public SetPasswordTask(Application app, String passwd) {
        super(app);

        pwd = passwd;
        device = ((DigiHdmiApp)app).getDevice();
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    protected Object doInBackground() throws Exception {
        if (pwd == null || pwd.isEmpty()) {
            message("badpass");
        } else {
            message("settingPassword");
            deviceChangePassword();
            message("passwordSet");
        }
        return null;
    }

    protected abstract void deviceChangePassword() ;

}
