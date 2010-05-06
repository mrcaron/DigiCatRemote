/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.tasks.InitMatrixTask;
import com.intelix.digihdmi.app.tasks.MakeConnectionTask;
import com.intelix.digihdmi.model.Device;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
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

    @Action
    public Task setConnection(ActionEvent e) {
        AbstractButton b = (AbstractButton) e.getSource();
        String toggleInfo = b.getName();  // Name is "b_<OUTPUT>_<INPUT>"
        String[] parts = toggleInfo.split("_");

        return new MakeConnectionTask(appInstance, Integer.parseInt(parts[2])+1, Integer.parseInt(parts[1])+1);
    }
}
