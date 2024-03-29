/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.tasks.ToggleLockTask;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author developer
 */
public class LockActions {
    DigiHdmiApp app;
    boolean lockEnabled;

    public LockActions() {
        app = (DigiHdmiApp) Application.getInstance();
    }

    public boolean isLockEnabled() {
        return lockEnabled;
    }

    public void setLockEnabled(boolean lockEnabled) {
        this.lockEnabled = lockEnabled;
    }

    @Action/*(enabledProperty="lockEnabled")*/
    public Task lock()
    {
        int result = JOptionPane.showConfirmDialog(
                ((DigiHdmiApp)app).getMainFrame(), // Modal against main frame
                "Really lock the device?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
                );
        if (result == JOptionPane.YES_OPTION)
        {
            Task t = new ToggleLockTask(app, true /* Lock it */);
            
            return t;
        } else
            return null;
    }

    @Action
    public Task unlock()
    {
        JPasswordField pwd = new JPasswordField(18);
        int result = JOptionPane.showConfirmDialog(
            ((DigiHdmiApp)app).getMainFrame(),
            pwd,
            "Enter Password",
            JOptionPane.OK_CANCEL_OPTION
            );
        if (result != JOptionPane.CANCEL_OPTION)
        {
            char[] pswdChars = pwd.getPassword();
            return new ToggleLockTask(app, false /*unlock*/, new String(pswdChars));
        }
        else
            return null;
    }
}
