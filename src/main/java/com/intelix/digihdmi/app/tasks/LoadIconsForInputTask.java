package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Connector;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.Action;
import org.jdesktop.application.Application;

/**
 *
 * @author mcaron
 */
public class LoadIconsForInputTask extends LoadIconsTask {

    public LoadIconsForInputTask(Application app, Connector c) {
        super(app,c);
    }

    @Override
    protected List<String> getIconList() {
        List<String> l = new ArrayList<String>();

        int numIcons = ((DigiHdmiApp)getApplication()).getNumInputIcons();

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(2);

        for (int i=1; i<=numIcons; i++)
            l.add("input_" + nf.format(i));

        return l;
    }

    @Override
    protected Action getButtonAction() {
        return null;
    }

}
