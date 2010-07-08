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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class DevicePrefsDlg extends JDialog {

    private JTextField fldNumInputs;
    private JTextField fldNumOutputs;
    private JTextField fldNumPresets;
    private JTextField fldPresetNameLength;
    private JTextField fldLockPassLength;
    private JTextField fldAdminPassLength;

    private JButton btnConnection;
    private JButton btnOk;
    private JButton btnCancel;

    public DevicePrefsDlg() {
        super();
        init();
    }

    public DevicePrefsDlg(JDialog f) {
        super(f);
        init();
    }

    public DevicePrefsDlg(JFrame f) {
        super(f);
        init();
    }
    
    public void init() {
        setModal(true);
        setTitle("Device Preferences");
        initializeComponents();
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        JPanel p = new JPanel();
        p.setLayout(new MigLayout((System.getProperty("DEBUG_UI") == null ? "" : "debug,")));

        p.add(new JLabel("Number of Inputs:"));
        fldNumInputs = new JTextField(4);
        p.add(fldNumInputs);

        p.add(new JLabel("Preset Name Length:"));
        fldPresetNameLength = new JTextField(4);
        p.add(fldPresetNameLength, "wrap");

        p.add(new JLabel("Number of Outputs:"));
        fldNumOutputs = new JTextField(4);
        p.add(fldNumOutputs);

        p.add(new JLabel("Max Admin Password Length:"));
        fldAdminPassLength = new JTextField(4);
        p.add(fldAdminPassLength, "wrap");

        p.add(new JLabel("Number of Presets:"));
        fldNumPresets = new JTextField(4);
        p.add(fldNumPresets);

        p.add(new JLabel("Max Lock Password Length:"));
        fldLockPassLength = new JTextField(4);
        p.add(fldLockPassLength, "wrap");

        btnConnection = new JButton("Connection...");
        p.add(btnConnection);
        btnOk = new JButton("Ok");
        p.add(btnOk, "tag ok, span, split");
        btnCancel = new JButton("Cancel");
        p.add(btnCancel, "tag cancel");

        setContentPane(p);
        pack();
    }

    public void setBtnCancelAction(Action action)
    {
        btnCancel.setAction(action);
    }

    public void setBtnOkAction(Action action)
    {
        btnOk.setAction(action);
    }

    public void setBtnConnectionAction(Action action)
    {
        btnConnection.setAction(action);
    }

    public int getAdminPassLength() {
        return Integer.parseInt(fldAdminPassLength.getText());
    }

    public void setAdminPassLength(int adminPassLength) {
        fldAdminPassLength.setText(""+adminPassLength);
    }

    public int getNumInputs() {
        return Integer.parseInt(fldNumInputs.getText());
    }

    public void setNumInputs(int numInputs) {
        fldNumInputs.setText(""+numInputs);
    }

    public int getNumOutputs() {
        return Integer.parseInt(fldNumOutputs.getText());
    }

    public void setNumOutputs(int numOutputs) {
        fldNumOutputs.setText(""+numOutputs);
    }

    public int getNumPresets() {
        return Integer.parseInt(fldNumPresets.getText());
    }

    public void setNumPresets(int numPresets) {
        fldNumPresets.setText(""+numPresets);
    }

    public int getPresetNameLength() {
        return Integer.parseInt(fldPresetNameLength.getText());
    }

    public void setPresetNameLength(int presetNameLength) {
        fldPresetNameLength.setText(""+presetNameLength);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //System.setProperty("DEBUG_UI", "true");
                final DevicePrefsDlg d = new DevicePrefsDlg();
                d.setVisible(true);
                d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            }
        });
    }
}
