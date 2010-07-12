package com.intelix.digihdmi.app.views;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author mcaron
 */
public class CustomizeConnectorPanel extends ApplicationView {

    private JButton btnDefIcon;
    private JButton btnDefText;

    public void setBtnDefIconAction(Action action){ btnDefIcon.setAction(action); }
    public void setBtnDefTextAction(Action action){ btnDefText.setAction(action); }

    @Override
    protected JComponent createRightComponent() {
        JPanel p = new JPanel();

        btnDefIcon = new JButton("Define Icon");
        btnDefIcon.setOpaque(false);
        btnDefText = new JButton("Define Text");
        btnDefText.setOpaque(false);

        p.setLayout(new MigLayout(
                (System.getProperty("DEBUG_UI") == null ? "" : "debug,") +
                "al 50% 50%, gapy 10"));

        p.add(btnDefIcon,   "align center, growx, wrap");
        p.add(btnDefText,  "align center, growx, wrap");

        return p;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Customize Connector Panel");
                System.setProperty("DEBUG_UI", "true");
                CustomizeConnectorPanel lv = new CustomizeConnectorPanel();
                f.getContentPane().add(lv);
                f.setSize(700,400);
                f.setVisible(true);
            }
        });
    }

}

