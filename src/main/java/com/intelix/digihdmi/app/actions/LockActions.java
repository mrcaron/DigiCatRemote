/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

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
    public void unlock()
    {
        app.showHomeView();
    }
}
