package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.ButtonContainerPanel;
import com.intelix.digihdmi.app.views.IconListView;
import com.intelix.digihdmi.model.Connector;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JComponent;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author mcaron
 */
public abstract class LoadIconsTask extends Task {

    Connector selected;
    ResourceBundle config = null;
    List<String> iconList;
    private final ButtonContainerPanel panel;

    private ResourceBundle getConfiguration() {
        if (config == null) {
            config = ResourceBundle.getBundle(this.getClass().getCanonicalName());
        }
        return config;
    }

    public LoadIconsTask(Application app, Connector ctr) {
        super(app);
        selected = ctr;

        JComponent c = ((DigiHdmiApp) app).getCurrentView();
        if (c instanceof IconListView) {
            panel = ((IconListView) c).getButtonsPanel();
        } else {
            panel = null;
        }
    }

    @Override
    protected Object doInBackground() throws Exception {
        iconList = getIconList();

        Iterator icons = iconList.iterator();
        while (icons.hasNext())
        {
            panel.addButton("name", (String)icons.next(), getButtonAction());
            ((DigiHdmiApp) getApplication()).getCurrentView().validate();
        }

        Logger.getLogger(this.getClass().getCanonicalName()).info("Running load icons task");
        return null;
    }

    protected abstract List<String> getIconList();
    protected abstract Action getButtonAction();
}