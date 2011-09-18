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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class NameChangeDlg extends JDialog implements PropertyChangeListener, ActionListener {

    JOptionPane optionPane;
    JTextField tfName;
    String question;
    int maxChars;
    String theName;
    boolean cancelled = false;

    public NameChangeDlg(Frame f, String question, int maxChars, String defaultValue) {
        super(f, true);
        this.question = question;
        this.maxChars = maxChars;
        initializeComponents();
        
        tfName.setText(defaultValue);
        tfName.selectAll();
        tfName.requestFocus();

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
    
    public NameChangeDlg(Frame f, String question, int maxChars) {
        this(f,question,maxChars,"");
    }

    private void initializeComponents()
    {
        //JPanel p = new JPanel();
        //p.setLayout(new MigLayout((System.getProperty("DEBUG_UI") == null ? "" : "debug,")));

        tfName = new JTextField();

        optionPane = new JOptionPane(
                new Object[] {question /*"Type a new name for " + identifier*/, tfName },
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null);
        setContentPane(optionPane);
        pack();

        // make text field request focus
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                tfName.requestFocus();
            }
        });

        tfName.setDocument(new JTextFieldLimit(maxChars));
        tfName.addActionListener(this);
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
            theName = tfName.getText();
            cancelled = false;
        } else {
            cancelled = true;
        }
        
        tfName.setText(null);
        setVisible(false);
    }

    public String getTheName() {
        return theName;
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
                final NameChangeDlg d = new NameChangeDlg(f,"01",5);
                f.setSize(700,400);
                f.setLocationRelativeTo(null);

                f.pack();

                f.setVisible(true);
                d.setVisible(true);

                label.setText(d.getTheName());

            }
        });
    }
}
