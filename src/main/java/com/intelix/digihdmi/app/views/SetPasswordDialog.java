package com.intelix.digihdmi.app.views;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class SetPasswordDialog extends JDialog {

    JPasswordField tfPass1;
    JPasswordField tfPass2;
    JButton btnOK;
    JButton btnCancel;
    String password;

    public SetPasswordDialog(Window w) {
        super(w);

        initializeComponents();
    }

    public void initializeComponents()
    {
        setTitle("Change password");

        JPanel p = new JPanel();
        p.setLayout(new MigLayout((System.getProperty("DEBUG_UI") == null ? "" : "debug,")));

        tfPass1 = new JPasswordField(80);
        tfPass2 = new JPasswordField(80);

        p.add(new JLabel("Enter password:"));
        p.add(tfPass1, "growx, width 200!, wrap");
        p.add(new JLabel("Confirm password:"));
        p.add(tfPass2, "growx, width 200!, wrap");

        btnOK = new JButton("Ok");
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                password = new String(tfPass1.getPassword());
                SetPasswordDialog.this.setVisible(false);
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                password = "";
                SetPasswordDialog.this.setVisible(false);
            }
        });

        p.add(btnOK, "tag ok, span 2");
        p.add(btnCancel, "tag cancel");

        setContentPane(p);
        pack();
    }

    public String getValidPass() {
        return password;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JLabel label = new JLabel("password is: ");
                final JLabel passLabel = new JLabel("");

                final JFrame f = new JFrame("Test Panel");

                JPanel p = new JPanel(new MigLayout());
                p.add(label);
                p.add(passLabel);
                f.setContentPane(p);

                System.setProperty("DEBUG_UI", "true");
                final SetPasswordDialog d = new SetPasswordDialog(f);
                f.setSize(700,400);

                f.pack();

                f.setVisible(true);
                d.setVisible(true);
            }
        });
    }

}
