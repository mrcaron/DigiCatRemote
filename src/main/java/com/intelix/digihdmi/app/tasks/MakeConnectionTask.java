package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.ButtonContainerPanel;
import com.intelix.digihdmi.app.views.ButtonListView;
import com.intelix.digihdmi.model.Device;
import javax.swing.JOptionPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class MakeConnectionTask extends Task {

    private int inputNumber;
    private int oldInput;

    public MakeConnectionTask(Application app, int inputNumber) {
        super(app);
        this.inputNumber = inputNumber;
    }

    @Override
    protected Object doInBackground() throws Exception {
        Device device = ((DigiHdmiApp) Application.getInstance()).getDevice();

        String oldName = device.getSelectedInput().getName();
        int index = oldName.lastIndexOf('_') + 1;
        this.oldInput = Integer.parseInt(oldName.substring(index));

        device.setSelectedInput(this.inputNumber);
        device.makeConnection();
        return null;
    }

    @Override
    protected void succeeded(Object result) {
        super.succeeded(result);
        setMessage("Good to go.");
        JOptionPane.showMessageDialog(((DigiHdmiApp) Application.getInstance()).getMainFrame(), "Connection Made! Input:" + this.inputNumber);
    }

    @Override
    protected void failed(Throwable cause) {
        super.failed(cause);

        setMessage("Problem!");
        ButtonListView view = (ButtonListView) ((DigiHdmiApp) Application.getInstance()).getCurrentView();
        ButtonContainerPanel panel = view.getButtonsPanel();

        panel.getButton(this.oldInput - 1).setSelected(true);
    }
}