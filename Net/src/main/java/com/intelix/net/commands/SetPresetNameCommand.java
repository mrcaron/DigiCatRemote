/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.IdNamePayload;
import java.util.ResourceBundle;

/**
 *
 * @author mcaron
 */
public class SetPresetNameCommand extends Command {
    
    private static int MAX_PRESET_NAME_LENGTH = -1;

    private static int MAX_PRESET_NAME_LENGTH() {
        ResourceBundle config = ResourceBundle.getBundle("Device");
        if (MAX_PRESET_NAME_LENGTH < 0)
            MAX_PRESET_NAME_LENGTH = Integer.parseInt(
                config.getString("MAX_PRESET_NAME_LENGTH"));

        return MAX_PRESET_NAME_LENGTH;
    }

    public SetPresetNameCommand(int number, String name) {

        setClassNo(3);
        setIdNo(3);

        IdNamePayload p = new IdNamePayload(1 + MAX_PRESET_NAME_LENGTH(), name, number);
        setPayload(p);
    }

}
