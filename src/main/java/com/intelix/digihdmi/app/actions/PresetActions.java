package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.tasks.ApplyPresetTask;
import com.intelix.digihdmi.app.tasks.LoadPresetsListTask;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

public class PresetActions {

    DigiHdmiApp appInstance = null;

    public PresetActions() {
        appInstance = (DigiHdmiApp) Application.getInstance();
    }

    @Action
    public Task showPresetListForLoad() {
        return new LoadPresetsListTask(Application.getInstance());
    }

    @Action
    public Task showPresetListForSave() {
        return null;
    }

    @Action
    public Task applyPresetAndShowMatrixView(ActionEvent ev) {
        AbstractButton b = (AbstractButton) ev.getSource();
        int index = b.getName().lastIndexOf('_') + 1;
        int presetNumber = Integer.parseInt(b.getName().substring(index));

        // show the matrix view
        appInstance.showMatrixView();

        // populate the matrix view with results from preset application
        return new ApplyPresetTask(appInstance, presetNumber);
    }
}
