package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.tasks.ToggleDeviceConnectTask;
import com.intelix.digihdmi.util.Selectable;
import java.awt.event.ActionEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class DeviceActions {

    // This code is virtually duplicated in ConnectionDialogActions.onConnect
    @Action
    public Task toggleDeviceConnect(ActionEvent av) {
        return new ToggleDeviceConnectTask(Application.getInstance(),
                av.getSource() instanceof Selectable ? (Selectable)av.getSource() : null);
    }
}
