/*
 * Copyright 2010, Intelix, LLC. All rights reserved.
 */

package com.intelix.digihdmi.app.views;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class SynchronizationDlg extends JDialog {
    private JButton btnRead;
    private JButton btnWrite;
    private JButton btnCancel;

    public SynchronizationDlg(JFrame f) {
        super(f);

        setModal(true);
        setTitle("Device Synchronization");
        initializeComponents();
    }

    private void initializeComponents() {
        JPanel p = new JPanel();
        p.setLayout(new MigLayout((System.getProperty("DEBUG_UI") == null ? "" : "debug,"),"","[]20[]"));

        // Use HTML tags to create a line break in the JLabel text
        JLabel l = new JLabel("<html>Choose either to pull configuration from device<br/> "
                + "or push current configuration to device:</html>");

        p.add(l,"wrap");

        btnRead = new JButton("Read");
        p.add(btnRead, "tag other, span, split");
        btnWrite = new JButton("Write");
        p.add(btnWrite, "tag other");
        btnCancel = new JButton("Cancel");
        p.add(btnCancel, "tag cancel");

        setContentPane(p);
        pack();
    }

    public void setBtnReadAction(Action a)
    {
        btnRead.setAction(a);
    }
    public void setBtnWriteAction(Action a)
    {
        btnWrite.setAction(a);
    }
    public void setBtnCancelAction(Action a)
    {
        btnCancel.setAction(a);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.setProperty("DEBUG_UI", "true");
                final SynchronizationDlg d = new SynchronizationDlg(null);
                d.setVisible(true);
                d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            }
        });
    }
}

