/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.tasks.SetAdminPasswordTask;
import com.intelix.digihdmi.app.tasks.SetPasswordTask;
import com.intelix.digihdmi.app.tasks.SetUnlockPasswordTask;
import com.intelix.digihdmi.app.views.dialogs.SetPasswordDialog;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author developer
 */
public class AdminActions {

    @Action
    public Task setAdminPassword() {
        return setPassword(new SetAdminPasswordTask(Application.getInstance()));
    }
    
    @Action
    public Task setUnlockPassword() {
        return setPassword(new SetUnlockPasswordTask(Application.getInstance()));
    }

    private Task setPassword(SetPasswordTask t) {

        SetPasswordDialog d = new SetPasswordDialog(((DigiHdmiApp)Application.getInstance()).getMainFrame());
        d.setLocationRelativeTo(null);
        d.setVisible(true);

        if (!d.isCancelled())
        {
            String password = d.getValidPass();
            t.setPwd(password);
            return t;
        }

        return null;
    }

    @Action
    public void defineInputs() {}

    @Action
    public void defineOutputs() {}

}
