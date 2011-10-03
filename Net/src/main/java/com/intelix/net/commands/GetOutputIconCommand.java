package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.SequencePayload;

public class GetOutputIconCommand extends Command {

    public GetOutputIconCommand(int id) {
        setClassNo(1);
        setIdNo(10);
        SequencePayload p = new SequencePayload();
        p.add(id);
        setPayload(p);
    }
}
