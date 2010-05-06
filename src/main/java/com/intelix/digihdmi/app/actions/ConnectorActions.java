package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.tasks.GetInputsTask;
import com.intelix.digihdmi.app.tasks.GetOutputsTask;
import com.intelix.digihdmi.app.tasks.MakeConnectionTask;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class ConnectorActions {

    DigiHdmiApp appInstance;

    public ConnectorActions() {
        appInstance = (DigiHdmiApp) Application.getInstance();
    }

    @Action (block=Task.BlockingScope.WINDOW)
    public Task showInputListForSelection(ActionEvent ev) {
        JButton b = (JButton) ev.getSource();
        int index = b.getName().lastIndexOf('_') + 1;
        int outputNumber = Integer.parseInt(b.getName().substring(index));
        appInstance.getDevice().setSelectedOutput(outputNumber);
        appInstance.showRoomSelectionView();

        Task t = new GetInputsTask(appInstance);
        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }

    @Action (block=Task.BlockingScope.WINDOW)
    public Task connectInputAndOutput(ActionEvent ev) {
        AbstractButton b = (AbstractButton) ev.getSource();
        int index = b.getName().lastIndexOf('_') + 1;
        int inputNumber = Integer.parseInt(b.getName().substring(index));

        // avoid setting the selected input here because if the task fails, we
        // want the task to reset to the previously selected without another
        // round trip to the device.
        MakeConnectionTask t = new MakeConnectionTask(appInstance, inputNumber);

        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }

    @Action (block=Task.BlockingScope.WINDOW)
    public Task showOutputList() {
        Task t = new GetOutputsTask(appInstance);
        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }
}