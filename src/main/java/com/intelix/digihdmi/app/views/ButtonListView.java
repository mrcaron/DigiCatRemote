package com.intelix.digihdmi.app.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class ButtonListView extends ApplicationView {

    private ButtonContainerPanel buttonsPanel;
    private JScrollPane scrollPane;

    public ButtonContainerPanel getButtonsPanel() {
        return buttonsPanel;
    }

    protected ButtonContainerPanel createButtonsPanel() {
        return new ButtonContainerPanel();
    }

    @Override
    protected JComponent createRightComponent() {
        buttonsPanel = createButtonsPanel();
        scrollPane = new JScrollPane(buttonsPanel);
        return scrollPane;
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
