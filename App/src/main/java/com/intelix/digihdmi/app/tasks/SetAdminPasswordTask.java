package com.intelix.digihdmi.app.tasks;

import org.jdesktop.application.Application;

/**
 *
 * @author mcaron
 */
public class SetAdminPasswordTask extends SetPasswordTask {

    public SetAdminPasswordTask(Application app) {
        super(app);
    }

    public SetAdminPasswordTask(Application app, String passwd) {
        super(app, passwd);
    }

    @Override
    protected void deviceChangePassword() {
        device.setAdminPassword(pwd);
    }

}
