package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Connector;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import org.jdesktop.application.Application;

/**
 *
 * @author mcaron
 */
public class LoadIconsForOutputTask extends LoadIconsTask {

    public LoadIconsForOutputTask(Application app, Connector c) {
        super(app,c);
    }

    @Override
    protected List<String> getIconList() {
        List<String> l = new ArrayList<String>();

        int numIcons = ((DigiHdmiApp)getApplication()).getNumOutputIcons();

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(2);
        for (int i=1; i<=numIcons; i++)
            l.add("output_" + nf.format(i));

        return l;
    }

    @Override
    protected Action getButtonAction() {
        return null;
    }

}
