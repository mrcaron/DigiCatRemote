/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.commands;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
import com.intelix.net.Command;
import com.intelix.net.payload.IdNamePayload;
import java.util.ResourceBundle;
public class SetConnectorNameCommand extends Command {
    
    private static int MAX_IO_NAME_LENGTH = -1;

    private static int MAX_IO_NAME_LENGTH()
    {
        if (MAX_IO_NAME_LENGTH < 0)
        {
            ResourceBundle config = ResourceBundle.getBundle("Device");
            MAX_IO_NAME_LENGTH = Integer.parseInt(
                config.getString("MAX_IO_NAME_LENGTH"));
        }

        return MAX_IO_NAME_LENGTH;
    }

    public SetConnectorNameCommand(String name, int number, boolean isInput) {
        setClassNo(1);
        setIdNo(isInput ? 0 : 6);
        IdNamePayload p = new IdNamePayload(MAX_IO_NAME_LENGTH(), name, number);
        setPayload(p);
    }

}
