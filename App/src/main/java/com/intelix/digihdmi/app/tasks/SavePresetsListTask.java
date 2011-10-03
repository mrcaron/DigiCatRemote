/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.tasks;

import org.jdesktop.application.Application;

/**
 *
 * @author mcaron
 */
public class SavePresetsListTask extends PresetsListTask {

    public SavePresetsListTask(Application app) {
        super(app);
    }

    @Override
    protected String getActionName() {
        return "savePresetAndShowMatrixView";
    }

}
