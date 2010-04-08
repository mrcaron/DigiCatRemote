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

    @Action
    public Task showInputListForSelection(ActionEvent ev) {
        JButton b = (JButton) ev.getSource();
        int index = b.getName().lastIndexOf('_') + 1;
        int outputNumber = Integer.parseInt(b.getName().substring(index));

        ((DigiHdmiApp) Application.getInstance()).getDevice().setSelectedOutput(outputNumber - 1);

        ((DigiHdmiApp) Application.getInstance()).showRoomSelectionView();

        return new GetInputsTask(Application.getInstance());
    }

    @Action
    public Task connectInputAndOutput(ActionEvent ev) {
        AbstractButton b = (AbstractButton) ev.getSource();
        int index = b.getName().lastIndexOf('_') + 1;
        int inputNumber = Integer.parseInt(b.getName().substring(index));
        MakeConnectionTask t = new MakeConnectionTask(Application.getInstance(), inputNumber);
        return t;
    }

    @Action
    public Task showOutputList() {
        return new GetOutputsTask(Application.getInstance());
    }
}