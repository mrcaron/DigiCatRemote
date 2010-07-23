package com.intelix.digihdmi.app.views;

import com.intelix.digihdmi.util.IconImageButton;
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

        btnSavePreset = new IconImageButton("Save_underBtn");
        btnSavePreset.setName("btnSavePreset");
        btnSavePreset.setOpaque(false);
        btnLoadPreset = new IconImageButton("Load_underBtn");
        btnLoadPreset.setName("btnLoadPreset");
        btnLoadPreset.setOpaque(false);

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
