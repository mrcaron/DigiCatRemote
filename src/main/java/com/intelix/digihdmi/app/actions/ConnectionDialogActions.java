package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.views.dialogs.DeviceConnectionDlg;
import com.intelix.digihdmi.model.Device;
import com.intelix.digihdmi.util.Selectable;
import com.intelix.net.Connection;
import com.intelix.net.IPConnection;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class ConnectionDialogActions {
    DigiHdmiApp app;
    DeviceConnectionDlg dlg;
    boolean connected_while_in_dialog = false;

    public ConnectionDialogActions() {
        app = (DigiHdmiApp)Application.getInstance();
        dlg = app.getConnectionDlg();
    }

    @Action
    public void onOk()
    {
        alterDevice();
        dlg.setVisible(false);
        if (connected_while_in_dialog)
            app.showSyncDlg();
        connected_while_in_dialog = false;
    }

    protected IPConnection getCxnInfo()
    {
        IPConnection newC = new IPConnection();
        newC.setIpAddr(dlg.getIpAddr());
        newC.setPort(dlg.getPort());
        return newC;
    }

    protected Connection[] alterDevice() {
        Device device = app.getDevice();

        Connection oldC = device.getConnection();
        IPConnection newC = getCxnInfo();
        if ( !((IPConnection)oldC).getIpAddr().equals(dlg.getIpAddr()) ||
             ! (((IPConnection)oldC).getPort() == newC.getPort()))
        {
            try {
                device.setConnection(newC);
            } catch (IOException ex)
            {
                JOptionPane.showMessageDialog(dlg, "Connection failed!\n\nDetails:\n\t" + ex.getMessage(), "Fail!", JOptionPane.ERROR_MESSAGE);
            }
        }
        return new Connection[] {oldC,newC};
    }

    @Action
    public void onCancel()
    {
        dlg.setVisible(false);
        if (connected_while_in_dialog)
            app.showSyncDlg();
    }

    @Action
    public void onTest()
    {
        Device device = app.getDevice();
        IPConnection cxn = getCxnInfo();
        boolean go = true;
        if (device.getConnection() instanceof IPConnection)
        {
            IPConnection c = (IPConnection)device.getConnection();
            if (cxn.getIpAddr().equals(c.getIpAddr()) &&
                cxn.getPort() == c.getPort())
                go = false;
        }
        //boolean prevConnected = cxns[0].isConnected();
        boolean fail = true;

        // No need to disconnect on a TEST.
        if (go)
            try {
                //if (prevConnected) { device.disconnect(); Thread.sleep(10);}
                //device.setConnection(cxns[1]);
                //Thread.sleep(10);
                cxn.connect();
                cxn.disconnect();
                fail = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Connection failed!\n\nDetails:\n\t" + ex.getMessage(), "Fail!", JOptionPane.ERROR_MESSAGE);
            }

        if (! fail)
            JOptionPane.showMessageDialog(dlg, "Success!",
                    "Win!", JOptionPane.INFORMATION_MESSAGE);

        /*
        try {
            device.setConnection(cxns[0]);
            if (prevConnected)
                device.connect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dlg, "Error reconnecting to previously set connection!\n"
                    + "Remaining in disconnected state.\n\nDetails:\n\t"
                    + ex.getMessage(),
                    "Fail!", JOptionPane.ERROR_MESSAGE);
        }*/
    }

    @Action
    public void onConnect(ActionEvent e)
    {
        Device device = app.getDevice();
        // This code is virtually duplicated in DeviceActions.toggleConnect
        if (device.isConnected())
            try {
                device.disconnect();
                connected_while_in_dialog = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Error disconnecting!\n\nDetails:\n\t"
                    + ex.getMessage(),
                    "Fail!", JOptionPane.ERROR_MESSAGE);
            }
        else
        {
            alterDevice();
            try {
                device.connect();
                connected_while_in_dialog = true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Error connecting!\n"
                    + "Remaining in disconnected state.\n\nDetails:\n\t"
                    + ex.getMessage(),
                    "Fail!", JOptionPane.ERROR_MESSAGE);
                if (e.getSource() instanceof Selectable)
                    ((Selectable)e.getSource()).setSelected(false);
            }
        }
    }
}
