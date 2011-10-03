/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.PairSequencePayload;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class GetCrosspointCommand extends Command {
    public GetCrosspointCommand(int id) {
        setClassNo(2);
        setIdNo(1);
        PairSequencePayload p = new PairSequencePayload();
        p.add(id, 0 /* Don't care with a Get */);
        setPayload(p);
    }
}
