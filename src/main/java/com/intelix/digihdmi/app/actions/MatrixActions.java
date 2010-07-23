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

    @Action (block=Task.BlockingScope.WINDOW)
    public Task loadMatrix() {
        Task t = new InitMatrixTask(appInstance);
        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }

    @Action (block=Task.BlockingScope.WINDOW)
    public Task loadMatrixConnections()
    {
        Task t = new InitMatrixTask(appInstance, false /* Don't load the names */);
        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }

    @Action (block=Task.BlockingScope.WINDOW)
    public Task setConnection(ActionEvent e) {
        AbstractButton b = (AbstractButton) e.getSource();
        String toggleInfo = b.getName();  // Name is "b_<OUTPUT>_<INPUT>"
        String[] parts = toggleInfo.split("_");

        Task t = new MakeConnectionTask(appInstance, Integer.parseInt(parts[2]), Integer.parseInt(parts[1]));
        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }
}
