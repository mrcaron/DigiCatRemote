package com.intelix.digihdmi.app.views;

import javax.swing.JButton;
import javax.swing.JComponent;
import org.jdesktop.application.Application;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class MatrixView extends ApplicationView {

    MatrixPanel panel;
    JButton btnSavePreset, btnLoadPreset;

    @Override
    protected JComponent createRightComponent() {
        // could be optimized by NOT creating a new view EACH time we get
        // the right panel of the view. This is not a great way to go.
        panel = new MatrixPanel();
        return panel;
    }

    @Override
    protected void initializeHomePanel() {
        super.initializeHomePanel();

        btnSavePreset = new JButton("Save Preset");
        btnSavePreset.setName("btnSavePreset");
        btnLoadPreset = new JButton("Load Preset");
        btnLoadPreset.setName("btnLoadPreset");

        // right here is breaking the snizzle...
        btnLoadPreset.setAction(
                Application.getInstance().getContext().getActionMap().get("showPresetListView"));
        btnSavePreset.setAction(
                Application.getInstance().getContext().getActionMap().get("showPresetSaveView"));

        homePanel.add(btnSavePreset);
        homePanel.add(btnLoadPreset);
    }



    public MatrixPanel getMatrixPanel() {
        return panel;
    }

}
