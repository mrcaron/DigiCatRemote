/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.tasks;

import com.intelix.digihdmi.app.DigiHdmiApp;
import com.intelix.digihdmi.model.Device;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

/**
 *
 * @author mcaron
 */
public class ToggleLockTask extends Task {

    boolean lock = false;
    String password;
    Device device;

    public ToggleLockTask(Application app) {
        this(app, false);
    }

    public ToggleLockTask(Application app, boolean lock) {
        this(app,lock,"");
    }

    public ToggleLockTask(Application app, boolean lock, String password)
    {
        super(app);
        device = ((DigiHdmiApp)app).getDevice();
        this.lock = lock;
        this.password = password;
    }

    @Override
    protected Object doInBackground() throws Exception {
        // connect to the device and lock it
        if (lock) {
            message("lockStart");
            device.lock();
            ((DigiHdmiApp)getApplication()).lockApp();
            message("lockEnd");
        } else {
            message("unlockStart");
            
            if (device.unlock(password))
            {
                ((DigiHdmiApp)getApplication()).showHomeView();
            }

            message("unlockEnd");
        }
        return null;
    }

}
