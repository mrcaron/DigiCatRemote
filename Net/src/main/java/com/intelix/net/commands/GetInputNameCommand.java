package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.IdNamePayload;

public class GetInputNameCommand extends Command {

    public GetInputNameCommand(int id) {
        setClassNo(1);
        setIdNo(1);
        IdNamePayload p = new IdNamePayload();
        p.setItemNo(id);
        setPayload(p);
    }
}
