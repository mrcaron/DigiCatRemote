package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.tasks.GetInputsForSelectionTask;
import com.intelix.digihdmi.app.tasks.GetInputsForCustomizationTask;
import com.intelix.digihdmi.app.tasks.GetOutputsForCustomizationTask;
import com.intelix.digihdmi.app.tasks.GetOutputsForSelectionTask;
import com.intelix.digihdmi.app.tasks.MakeConnectionTask;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class ConnectorActions {

    DigiHdmiApp appInstance;

    public ConnectorActions() {
        appInstance = (DigiHdmiApp) Application.getInstance();
    }

    protected int getSelectedConnector(AbstractButton b)
    {
        int index = b.getName().lastIndexOf('_') + 1;
        return Integer.parseInt(b.getName().substring(index));
    }

    @Action (block=Task.BlockingScope.WINDOW)
    public Task showInputListForSelection(ActionEvent ev) {
        int outputNumber = getSelectedConnector((AbstractButton) ev.getSource());
        appInstance.getDevice().setSelectedOutput(outputNumber);
        appInstance.showInputSelectionView();

        Task t = new GetInputsForSelectionTask(appInstance);
        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }

    @Action (block=Task.BlockingScope.WINDOW)
    public Task showOutputListForCustomization() {
        appInstance.showOutputCustomizationView();
        Task t = new GetOutputsForCustomizationTask(appInstance);
        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }

    @Action (block=Task.BlockingScope.WINDOW)
    public Task showInputListForCustomization() {
        appInstance.showInputCustomizationView();
        Task t = new GetInputsForCustomizationTask(appInstance);
        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }

    @Action
    public void changeInput(ActionEvent ev)
    {
        int index = getSelectedConnector((AbstractButton) ev.getSource());
        appInstance.getDevice().setSelectedInput(index);
        appInstance.showInputChangeView();
    }

    @Action
    public void changeOutput(ActionEvent ev)
    {
        int index = getSelectedConnector((AbstractButton) ev.getSource());
        appInstance.getDevice().setSelectedOutput(index);
        appInstance.showOutputChangeView();
    }

    @Action (block=Task.BlockingScope.WINDOW)
    public Task connectInputAndOutput(ActionEvent ev) {
        int index = getSelectedConnector((AbstractButton) ev.getSource());

        // avoid setting the selected input here because if the task fails, we
        // want the task to reset to the previously selected without another
        // round trip to the device.
        MakeConnectionTask t = new MakeConnectionTask(appInstance, index);

        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }

    @Action (block=Task.BlockingScope.WINDOW)
    public Task showOutputList() {
        Task t = new GetOutputsForSelectionTask(appInstance);
        t.setInputBlocker(appInstance.new BusyInputBlocker(t));
        return t;
    }

    @Action 
    public void assignNewName(ActionEvent ev)
    {
        // show a dialog to capture a name and use the selected button to
        // assign new name to device's input
        JOptionPane.showMessageDialog(null, "Assign New Name!");
    }
}