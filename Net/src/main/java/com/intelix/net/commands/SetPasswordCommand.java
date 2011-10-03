/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.SequencePayload;
import java.util.ResourceBundle;

/**
 *
 * @author mcaron
 */
public abstract class SetPasswordCommand extends Command {

    protected int MAX_LENGTH = -1;

    public SetPasswordCommand(String password) {}

    protected SetPasswordCommand(String password, int id, String propName) {
        ResourceBundle config = ResourceBundle.getBundle("Device");
        if (MAX_LENGTH < 0)
            MAX_LENGTH = Integer.parseInt(
                config.getString(propName));

        setClassNo(0);
        setIdNo(id);
        SequencePayload p = new SequencePayload(MAX_LENGTH);
        for(int i=0; i<MAX_LENGTH || i<password.length(); i++)
        {
            p.add(password.charAt(i));
        }

        setPayload(p);
    }

}
