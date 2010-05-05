/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.tasks.InitMatrixTask;
import com.intelix.digihdmi.model.Device;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author mcaron
 */
public class MatrixActions {

    DigiHdmiApp appInstance;
    Device device;

    public MatrixActions() {
        appInstance = (DigiHdmiApp) Application.getInstance();
        device = appInstance.getDevice();
    }

    @Action
    public Task loadMatrix() {
        return new InitMatrixTask(appInstance);
    }
}
