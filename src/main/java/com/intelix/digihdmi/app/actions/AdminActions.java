/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.tasks.SetAdminPasswordTask;
import com.intelix.digihdmi.app.views.DigiHdmiAppMainView;
import com.intelix.digihdmi.app.views.SetPasswordDialog;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author developer
 */
public class AdminActions {

    @Action
    public Task setPassword() {
        SetPasswordDialog d = new SetPasswordDialog(((DigiHdmiApp)Application.getInstance()).getMainFrame());
        d.setLocationRelativeTo(null);
        d.setVisible(true);

        if (!d.isCancelled())
        {
            String password = d.getValidPass();
            return new SetAdminPasswordTask(Application.getInstance(), password);
        }

        return null;
    }

    @Action
    public void defineInputs() {}

    @Action
    public void defineOutputs() {}

}
