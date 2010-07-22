package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.actions.AdminActions;
import com.intelix.digihdmi.app.actions.ConnectorActions;
import com.intelix.digihdmi.app.views.IconContainerPanel;
import com.intelix.digihdmi.model.Connector;
import java.text.NumberFormat;
import javax.swing.Action;
import org.jdesktop.application.Application;

/**
 *
 * @author mcaron
 */
public class LoadIconsForOutputTask extends LoadIconsTask {

    NumberFormat nf = NumberFormat.getInstance();

    public LoadIconsForOutputTask(Application app, IconContainerPanel p)
    {
        super(app,p);
        nf.setMinimumIntegerDigits(2);
    }

    public LoadIconsForOutputTask(Application app, Connector c) {
        super(app,c);
    }

    @Override
    protected Action getButtonAction() {
        return app.getContext().getActionMap(new ConnectorActions()).get("assignOutputIcon");
    }

    @Override
    protected String getIconResourceString(int index) {
        return "output_" + nf.format(index);
    }

}
