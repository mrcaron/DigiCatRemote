package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.SequencePayload;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class TriggerPresetCommand extends Command {

    public TriggerPresetCommand(int presetNum) {
        setClassNo(3);
        setIdNo(6);
        SequencePayload p = new SequencePayload(1);
        p.add(presetNum);
        setPayload(p);
    }
    
}
