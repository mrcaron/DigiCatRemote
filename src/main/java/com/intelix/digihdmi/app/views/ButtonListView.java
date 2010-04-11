package com.intelix.digihdmi.app.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.Application;

public class ButtonListView extends JPanel {

    private ButtonContainerPanel buttonsPanel;
    private JScrollPane scrollPane;
    private JButton btnHome;
    protected JPanel homePanel;
    protected JPanel bottomPanel;

    public ButtonListView() {
        initializeComponents();
    }

    public void setHomeAction(Action a) {
        btnHome.setAction(a);
    }

    public ButtonContainerPanel getButtonsPanel() {
        return buttonsPanel;
    }

    private void initializeComponents() {
        setMinimumSize(new Dimension(600, 400));
        setPreferredSize(new Dimension(600, 400));
        setLayout(new MigLayout("", "[left]10[right,fill,grow]", "[][grow]"));

        initializeButtonsPanel();
        scrollPane = new JScrollPane(buttonsPanel);
        initializeHomePanel();
        initializeBottomPanel();

        add(homePanel, "aligny top, grow");
        add(scrollPane, "spany 2, grow, wrap");
        add(bottomPanel, "aligny bottom, grow");
    }

    protected JPanel createHomePanel() {
        return new JPanel(new MigLayout("wrap 1, aligny top", "[fill,grow]"));
    }

    protected void initializeHomePanel() {
        homePanel = createHomePanel();
        btnHome = new JButton();
        homePanel.add(btnHome);
        setHomeAction(Application.getInstance().getContext().getActionMap().get("showHomeView"));
    }

    protected JPanel createBottomPanel() {
        return new JPanel(new MigLayout("wrap 1, aligny bottom"));
    }

    protected void initializeBottomPanel() {
        bottomPanel = createBottomPanel();
    }

    protected ButtonContainerPanel createButtonsPanel() {
        return new ButtonContainerPanel();
    }

    protected void initializeButtonsPanel() {
        buttonsPanel = createButtonsPanel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame f = new JFrame();
                final ButtonListView m = new ButtonListView();
                f.setSize(new Dimension(600, 400));
                f.getContentPane().add(m);

                m.setHomeAction(new BasicAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "Hello World!");
                    }
                });

                SwingWorker w = new SwingWorker() {

                    @Override
                    protected Object doInBackground() throws Exception {
                        for (int i = 0; i < 4; ++i) {
                            Thread.sleep(1000L);
                            m.getButtonsPanel().addButton("Do Me.", new BasicAction() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JOptionPane.showMessageDialog(null, "Hello World!");
                                }
                            });
                        }
                        return null;
                    }
                };
                f.setVisible(true);
                w.execute();
            }
        });
    }
}

abstract class BasicAction
        implements Action {

    @Override
    public Object getValue(String key) {
        return null;
    }

    @Override
    public void putValue(String key, Object value) {
    }

    @Override
    public void setEnabled(boolean b) {
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
    }
}
