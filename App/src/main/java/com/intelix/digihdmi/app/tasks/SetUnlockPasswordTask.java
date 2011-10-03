package com.intelix.digihdmi.app.tasks;

import org.jdesktop.application.Application;

/**
 *
 * @author mcaron
 */
public class SetUnlockPasswordTask extends SetPasswordTask {

    public SetUnlockPasswordTask(Application app) {
        super(app);
    }

    public SetUnlockPasswordTask(Application app, String passwd) {
        super(app, passwd);
    }

    @Override
    protected void deviceChangePassword() {
        device.setUnlockPassword(pwd);
    }

}
