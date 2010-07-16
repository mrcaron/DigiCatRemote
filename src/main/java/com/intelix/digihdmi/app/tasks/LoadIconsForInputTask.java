package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.views.IconContainerPanel;
import com.intelix.digihdmi.model.Connector;
import java.text.NumberFormat;
import javax.swing.Action;
import org.jdesktop.application.Application;

/**
 *
 * @author mcaron
 */
public class LoadIconsForInputTask extends LoadIconsTask {

    NumberFormat nf = NumberFormat.getInstance();

    public LoadIconsForInputTask(Application app, IconContainerPanel p)
    {
        super(app,p);
        nf.setMinimumIntegerDigits(2);
    }

    public LoadIconsForInputTask(Application app, Connector c) {
        super(app,c);
    }

    @Override
    protected Action getButtonAction() {
        return null;
    }

    @Override
    protected String getIconResourceString(int index) {
        return "input_" + nf.format(index);
    }

}
