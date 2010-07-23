package com.intelix.digihdmi.app.views;

import com.intelix.digihdmi.util.IconImageButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.Application;

public class PresetLoadListView extends ButtonListView {

    JButton btnRefresh;

    @Override
    protected void initializeHomePanel() {
        super.initializeHomePanel();

        IconImageButton matrixView = new IconImageButton("MatrixIconBtn");

        matrixView.setAction(
            Application.getInstance().getContext().getActionMap().get("showAndLoadMatrixView")
        );
        this.homePanel.add(matrixView);
    }

    @Override
    protected JComponent createRightComponent() {
        JComponent c = super.createRightComponent();
        JPanel p = new JPanel(new MigLayout());

        p.add(c,"grow,push,wrap");

        // Add refresh button
        btnRefresh = new JButton("Refresh");
        p.add(btnRefresh, "split, alignx right");

        return p;
    }

    public void setBtnRefreshAction(Action a) {
        btnRefresh.setAction(a);
    }
}
