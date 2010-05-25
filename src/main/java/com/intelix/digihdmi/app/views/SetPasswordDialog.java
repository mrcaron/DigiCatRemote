package com.intelix.digihdmi.app.views;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
    boolean cancelled = false;

    public SetPasswordDialog(Window w) {
        super(w);

        setModal(true);
        setTitle("Change password");
        initializeComponents();
    }

    private void initializeComponents()
    {
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
                String p1 = new String(tfPass1.getPassword());
                String p2 = new String(tfPass2.getPassword());

                if (p1.equals(p2)) {
                    password = p1;
                SetPasswordDialog.this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords don't match!", "Error", JOptionPane.ERROR_MESSAGE);
                }

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

    // if this is null, then setting the password didn't work!
    public String getValidPass() {
        return password;
    }

    public boolean isCancelled() {
        return cancelled;
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
