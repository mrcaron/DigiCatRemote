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
    private int oldInputNumber;
    private Device device;

    public MakeConnectionTask(Application app, int inputNumber) {
        super(app);
        this.inputNumber = inputNumber;
        device = ((DigiHdmiApp)app).getDevice();

    }

    @Override
    protected Object doInBackground() throws Exception {
        message("startMessage");
        oldInputNumber = device.getSelectedInput().getIndex();
        device.setSelectedInput(inputNumber);
        
        try {
            message("makingConnection");
            boolean result = device.makeConnection();
            message("connectionMade");

        } catch (Exception e)
        {
            message("error");
            System.err.println("Exception processing connection : " + e.getMessage());
            e.printStackTrace(System.err);
        }
        return null;
    }

    @Override
    protected void succeeded(Object result) {
        super.succeeded(result);
        message("success");
        JOptionPane.showMessageDialog(((DigiHdmiApp)getApplication()).getMainFrame(),
                "Connection Made! Input:" + inputNumber);
    }

    @Override
    protected void failed(Throwable cause) {
        super.failed(cause);
        message("fail");
        ButtonListView view = (ButtonListView) ((DigiHdmiApp) getApplication()).getCurrentView();
        ButtonContainerPanel panel = view.getButtonsPanel();
        panel.getButton(oldInputNumber - 1).setSelected(true);
    }
}