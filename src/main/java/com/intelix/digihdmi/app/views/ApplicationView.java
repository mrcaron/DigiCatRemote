package com.intelix.digihdmi.app.views;

import java.awt.Dimension;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.Application;

public abstract class ApplicationView extends JPanel {

    private JComponent rightComponent;
    private JButton btnHome;
    protected JPanel homePanel;
    protected JPanel bottomPanel;

    public ApplicationView() {
        initializeComponents();
    }

    public void setHomeAction(Action a) {
        btnHome.setAction(a);
    }

    protected void initializeComponents() {
        //setMinimumSize(new Dimension(800, 400));
        //setPreferredSize(new Dimension(800, 400));
        setLayout(new MigLayout("", "[left]10[right,fill,grow]", "[][grow]"));

        initializeRightComponent();
        initializeHomePanel();
        initializeBottomPanel();

        add(homePanel, "aligny top, grow");
        add(rightComponent, "spany 2, grow, wrap");
        add(bottomPanel, "aligny bottom, grow");
    }

    protected JPanel createHomePanel() {
        return new JPanel(new MigLayout("wrap 1, aligny top", "[fill,grow]"));
    }

    protected void initializeHomePanel() {
        homePanel = createHomePanel();
        btnHome = new JButton("Home");
        homePanel.add(btnHome);
        setHomeAction(Application.getInstance().getContext().getActionMap().get("showHomeView"));
    }

    protected JPanel createBottomPanel() {
        return new JPanel(new MigLayout("wrap 1, aligny bottom"));
    }

    protected void initializeBottomPanel() {
        bottomPanel = createBottomPanel();
    }

    protected abstract JComponent createRightComponent();
    protected void initializeRightComponent()
    {
        rightComponent = createRightComponent();
    }
}