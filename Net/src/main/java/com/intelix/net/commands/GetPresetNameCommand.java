/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.IdNamePayload;

/**
 *
 * @author developer
 */
public class GetPresetNameCommand extends Command {
    public GetPresetNameCommand(int number)
    {
        setClassNo(3);
        setIdNo(4);
        IdNamePayload p = new IdNamePayload();
        p.setItemNo(number);
        setPayload(p);
    }
}
