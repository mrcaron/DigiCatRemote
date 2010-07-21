package com.intelix.digihdmi.app.views.dialogs;

import com.intelix.digihdmi.util.JTextFieldLimit;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class PasswordSubmissionDlg extends JDialog implements PropertyChangeListener, ActionListener {

    JOptionPane optionPane;
    JPasswordField tfPasswd;
    String question;
    int maxChars;
    String passwd;
    boolean cancelled = false;

    public PasswordSubmissionDlg(Frame f, int maxChars) {
        super(f, true);
        this.question = "Enter password:";
        this.maxChars = maxChars;
        initializeComponents();

        setLocationRelativeTo(f);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
            /*
             * Instead of directly closing the window,
             * we're going to change the JOptionPane's
             * value property.
             */
                optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
            }
        });
    }

    private void initializeComponents()
    {
        //JPanel p = new JPanel();
        //p.setLayout(new MigLayout((System.getProperty("DEBUG_UI") == null ? "" : "debug,")));

        tfPasswd = new JPasswordField();

        optionPane = new JOptionPane(
                new Object[] {question /*"Type a new name for " + identifier*/, tfPasswd },
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null);
        setContentPane(optionPane);
        pack();

        // make text field request focus
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                tfPasswd.requestFocus();
            }
        });

        tfPasswd.setDocument(new JTextFieldLimit(maxChars));
        tfPasswd.addActionListener(this);
        optionPane.addPropertyChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(JOptionPane.OK_OPTION);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {

        if (e.getNewValue() instanceof Integer &&
                (Integer)e.getNewValue() == JOptionPane.OK_OPTION)
        {
            passwd = new String(tfPasswd.getPassword());
            cancelled = false;
        } else {
            cancelled = true;
        }

        tfPasswd.setText(null);
        setVisible(false);
    }

    public String getPassword() {
        return passwd;
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
                JLabel label = new JLabel("Hello.");

                final JFrame f = new JFrame("Test Panel");

                JPanel p = new JPanel(new MigLayout());
                p.add(label);
                f.setContentPane(p);

                System.setProperty("DEBUG_UI", "true");
                final PasswordSubmissionDlg d = new PasswordSubmissionDlg(f,8);
                f.setSize(700,400);
                f.setLocationRelativeTo(null);

                f.pack();

                f.setVisible(true);
                d.setVisible(true);

                label.setText(d.getPassword());

            }
        });
    }
}
