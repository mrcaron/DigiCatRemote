/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.SequencePayload;

/**
 *
 * @author mcaron
 */
public class SetPresetCommand extends Command {

    public SetPresetCommand(int number) {
        super();
        setClassNo(3);
        setIdNo(0);
        SequencePayload p = new SequencePayload();
        p.add(number);
        setPayload(p);
    }
}
