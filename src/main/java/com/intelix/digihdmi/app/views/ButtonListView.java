package com.intelix.digihdmi.app.views;

import com.intelix.digihdmi.util.BasicAction;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
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
        buttonsPanel.setOpaque(false);
        scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        return scrollPane;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame f = new JFrame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
                            Thread.sleep(500L);
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
