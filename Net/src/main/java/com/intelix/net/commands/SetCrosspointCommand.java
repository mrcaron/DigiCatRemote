package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.PairSequencePayload;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class SetCrosspointCommand extends Command {
    public SetCrosspointCommand(int input, int output) {
        setClassNo(2);
        setIdNo(0);
        PairSequencePayload p = new PairSequencePayload();
        p.add(input, output);
        setPayload(p);
    }
}
