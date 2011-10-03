package com.intelix.digihdmi.app.tasks;

import org.jdesktop.application.Application;

public class LoadPresetsListTask extends PresetsListTask {
    public LoadPresetsListTask(Application app) {
        super(app);
        
    }

    @Override
    protected String getActionName() {
        return "applyPresetAndShowMatrixView";
    }
}
