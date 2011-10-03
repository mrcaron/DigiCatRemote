package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.SequencePayload;

public class GetInputIconCommand extends Command {

    public GetInputIconCommand(int id) {
        setClassNo(1);
        setIdNo(4);
        SequencePayload p = new SequencePayload();
        p.add(id);
        setPayload(p);
    }
}
