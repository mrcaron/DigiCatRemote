/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.tasks.ToggleLockTask;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author developer
 */
public class LockActions {
    DigiHdmiApp app;

    public LockActions() {
        app = (DigiHdmiApp) Application.getInstance();
    }

    @Action
    public Task unlock()
    {
        String password = JOptionPane.showInputDialog(
            ((DigiHdmiApp)Application.getInstance()).getMainFrame(),
            "Enter password:",
            "Unlock",
            JOptionPane.QUESTION_MESSAGE);
        return new ToggleLockTask(app, false /*unlock*/, password);
    }
}
