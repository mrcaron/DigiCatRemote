/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.views;

import com.intelix.digihdmi.util.TextImageButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author developer
 */
public class AdminPanel extends ApplicationView {

    private JButton btnSetPassword;
    private JButton btnDefineInputs;
    private JButton btnDefineOutputs;

    public void setBtnPsswdAction(Action action){ btnSetPassword.setAction(action); }
    public void setBtnDefineInputsAction(Action action){ btnDefineInputs.setAction(action); }
    public void setBtnDefineOutputsAction(Action action){ btnDefineOutputs.setAction(action); }

    @Override
    protected JComponent createRightComponent() {
        JPanel p = new JPanel();

        btnSetPassword = new TextImageButton("SetPasswordBtn");
        btnDefineInputs = new TextImageButton("DefInputsBtn");
        btnDefineOutputs = new TextImageButton("DefOutputsBtn");

        p.setLayout(new MigLayout(
                (System.getProperty("DEBUG_UI") == null ? "" : "debug,") +
                "al 50% 50%, gapy 10"));

        p.add(btnSetPassword,   "align center, wrap");
        p.add(btnDefineInputs,  "align center, wrap");
        p.add(btnDefineOutputs, "align center, wrap");

        return p;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Home Panel");
                System.setProperty("DEBUG_UI", "true");
                AdminPanel lv = new AdminPanel();
                f.getContentPane().add(lv);
                f.setSize(700,400);
                f.setVisible(true);
            }
        });
    }

}
