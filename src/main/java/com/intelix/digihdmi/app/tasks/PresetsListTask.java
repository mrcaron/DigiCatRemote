/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.actions.PresetActions;
import com.intelix.digihdmi.app.views.ButtonContainerPanel;
import com.intelix.digihdmi.app.views.ButtonListView;
import com.intelix.digihdmi.model.Device;
import com.intelix.digihdmi.model.Preset;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author mcaron
 */
public abstract class PresetsListTask extends Task /*implements PropertyChangeListener*/ {

    protected final Device device;
    protected final ButtonContainerPanel panel;

    public PresetsListTask(Application app) {
        super(app);

        this.device = ((DigiHdmiApp) app).getDevice();
        //device.addPropertyChangeListener(this);

        JComponent c = ((DigiHdmiApp) app).getCurrentView();
        if (c instanceof ButtonListView) {
            this.panel = ((ButtonListView) c).getButtonsPanel();
        } else {
            this.panel = null;
        }
    }

    @Override
    protected Object doInBackground() throws Exception {
        ActionMap map = getContext().getActionMap(new PresetActions());
        if ((this.panel != null) && (this.device != null)) {
            Enumeration presetList = this.device.getPresets();
            for (int i = 0; presetList.hasMoreElements(); i++) {
                message("loadingPresetD", i);
                Preset c = (Preset) presetList.nextElement();
                message("loadedPresetDS", i, c.getName());
                setProgress(i,0,device.getNumPresets());
                this.panel.addButton(c.getName(), null, map.get(getActionName()));
            }
        }
        return null;
    }

    protected abstract String getActionName();

    /* for property change listener
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("progress"))
        {
            setProgress((Float)evt.getNewValue());
        }
    }*/

}
