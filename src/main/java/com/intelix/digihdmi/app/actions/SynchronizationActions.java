package com.intelix.digihdmi.app.actions;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.app.tasks.PullFromDeviceTask;
import com.intelix.digihdmi.app.tasks.PushToDeviceTask;
import com.intelix.digihdmi.app.views.SynchronizationDlg;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class SynchronizationActions {
    SynchronizationDlg dlg;
    DigiHdmiApp app;

    public SynchronizationActions() {
        app = (DigiHdmiApp)Application.getInstance();
        dlg = app.getSyncDlg();
    }

    @Action(block=Task.BlockingScope.WINDOW)
    public Task onPush() {
        dlg.setVisible(false);

        Task t = new PushToDeviceTask(app);
        t.setInputBlocker(app.new BusyInputBlocker(t));
        return t;
    }

    @Action
    public void onPull() {
        dlg.setVisible(false);
        // then we just close the dialog because the default operation is to
        // pull lazily from the device.

        //Task t = new PullFromDeviceTask(app);
        //t.setInputBlocker(app.new BusyInputBlocker(t));
        //return t;
    }

    @Action
    public void onCancel() {
        try {
            app.getDevice().disconnect();
        } catch (IOException e)
        {
            Logger.getLogger(getClass().getCanonicalName()).log(Level.INFO, "Can't disconnect from device during synchronization cancellation.");
        }
        dlg.setVisible(false);
    }
}