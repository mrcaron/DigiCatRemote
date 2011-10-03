package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.IdNamePayload;

public class GetOutputNameCommand extends Command {

    public GetOutputNameCommand(int id) {
        setClassNo(1);
        setIdNo(7);
        IdNamePayload p = new IdNamePayload();
        p.setItemNo(id);
        setPayload(p);
    }
}
