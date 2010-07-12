package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.model.Connector;
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

        for (int i=9; i>=0; i--)
            l.add("brown-couch");

        return l;
    }

    @Override
    protected Action getButtonAction() {
        return null;
    }

}
