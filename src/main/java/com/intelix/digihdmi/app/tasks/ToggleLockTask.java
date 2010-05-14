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
            message("lockEnd");
        } else {
            message("unlockStart");
            
            // get actual pass digest
            byte[] passHash = device.getPasswordHash();
            
            // digest submitted password
            MessageDigest md;
            byte[] submittedDigest = null;
            try {
                md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                submittedDigest = md.digest();
            } catch (NoSuchAlgorithmException ex)
            {
                // bah!
            }

            // check equality
            if (passHash != null && submittedDigest != null &&
                java.util.Arrays.equals(passHash, submittedDigest))
            {
                ((DigiHdmiApp)getApplication()).showHomeView();
            }

            message("unlockEnd");
        }
        return null;
    }

}
