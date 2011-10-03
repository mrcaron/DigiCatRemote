/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.SequencePayload;

/**
 *
 * @author developer
 */
public class GetPresetCommand extends Command {

    public GetPresetCommand(int number) {
        super();
        setClassNo(3);
        setIdNo(1);
        SequencePayload p = new SequencePayload();
        p.add(number);
        setPayload(p);
    }
}
