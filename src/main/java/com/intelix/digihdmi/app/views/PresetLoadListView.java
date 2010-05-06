package com.intelix.digihdmi.app.views;

import javax.swing.JButton;
import org.jdesktop.application.Application;

public class PresetLoadListView extends ButtonListView {

    @Override
    protected void initializeHomePanel() {
        super.initializeHomePanel();

        JButton matrixView = new JButton();

        matrixView.setAction(
            Application.getInstance().getContext().getActionMap().get("showMatrixView")
        );
        this.homePanel.add(matrixView);
    }
}
