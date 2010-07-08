package com.intelix.digihdmi.app.views;

/*
 * Copyright 2010, Intelix, LLC. All rights reserved.
 */

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class DeviceConnectionDlg extends JDialog {
    private JTextField fldIpAddr;
    private JTextField fldPort;
    private JButton btnOk;
    private JButton btnCancel;
    private JButton btnTest;
    private JButton btnConnect;

    public DeviceConnectionDlg() {
        super();
        init();
    }

    public DeviceConnectionDlg(JFrame f) {
        super(f);
        init();
    }
    
    public DeviceConnectionDlg(JDialog f) {
        super(f);
        init();
    }

    protected void init()
    {
        setModal(true);
        setTitle("Device Connection");
        initializeComponents();
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        JPanel p = new JPanel();
        p.setLayout(new MigLayout((System.getProperty("DEBUG_UI") == null ? "" : "debug,")));

        p.add(new JLabel("IP Address:"));
        fldIpAddr = new JTextField(20);
        p.add(fldIpAddr, "wrap");

        p.add(new JLabel("Port:"));
        fldPort = new JTextField(20);
        p.add(fldPort, "wrap");

        btnOk = new JButton("Ok");
        p.add(btnOk, "tag ok, span, split");
        btnCancel = new JButton("Cancel");
        p.add(btnCancel, "tag cancel");
        btnTest = new JButton("Test");
        p.add(btnTest, "tag other");
        btnConnect = new JButton("Connect");
        p.add(btnConnect, "tag other");

        setContentPane(p);
        pack();
    }

    public String getPort()
    {
        return fldPort.getText();
    }

    public void setPort(String port)
    {
        fldPort.setText(port);
    }

    public String getIpAddr()
    {
        return fldIpAddr.getText();
    }

    public void setIpAddr(String ipAddr)
    {
        fldIpAddr.setText(ipAddr);
    }

    public void setBtnCancelAction(Action action)
    {
        btnCancel.setAction(action);
    }

    public void setBtnOkAction(Action action)
    {
        btnOk.setAction(action);
    }

    public void setBtnTestAction(Action action)
    {
        btnTest.setAction(action);
    }

    public void setBtnConnectAction(Action action)
    {
        btnConnect.setAction(action);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.setProperty("DEBUG_UI", "true");
                final DeviceConnectionDlg d = new DeviceConnectionDlg();
                d.setVisible(true);
                d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            }
        });
    }
}

