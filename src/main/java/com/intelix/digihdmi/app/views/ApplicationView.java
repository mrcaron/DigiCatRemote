package com.intelix.digihdmi.app.views;

import com.intelix.digihdmi.util.IconImageButton;
import com.intelix.digihdmi.util.PaintedJPanel;
import java.awt.Image;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.Application;

public abstract class ApplicationView extends PaintedJPanel {

    private JComponent rightComponent;
    private JButton btnHome;
    protected JPanel homePanel;
    protected JPanel home2Panel;
    private Image bgImage;

    public ApplicationView() {
        initializeComponents();
    }

    public void setHomeAction(Action a) {
        btnHome.setAction(a);
    }

    protected void initializeComponents() {
        //setMinimumSize(new Dimension(800, 400));
        //setPreferredSize(new Dimension(800, 400));
        setLayout(new MigLayout("", "[left]10[right,fill,grow]", "[][grow][]"));

        initializeRightComponent();
        initializeHomePanel();
        initializeHome2Panel();

        add(homePanel, "aligny top, grow");
        add(rightComponent, "spany 2, grow, wrap");
        add(home2Panel, "aligny bottom, grow, wrap");
    }

    protected JPanel createHomePanel() {
        JPanel p = new JPanel(new MigLayout("wrap 1, aligny top", "[grow]"));
        p.setOpaque(false);
        return p;
    }

    protected void initializeHomePanel() {
        homePanel = createHomePanel();
        btnHome = new IconImageButton("Home_under");
        homePanel.add(btnHome, "align center");
        setHomeAction(Application.getInstance().getContext().getActionMap().get("showHomeView"));
    }

    protected JPanel createHome2Panel() {
        JPanel p = new JPanel(new MigLayout("wrap 1, aligny bottom"));
        p.setOpaque(false);
        return p;
    }

    protected void initializeHome2Panel() {
        home2Panel = createHome2Panel();
    }

    protected abstract JComponent createRightComponent();
    protected void initializeRightComponent()
    {
        rightComponent = createRightComponent();
        rightComponent.setOpaque(false);
    }
}