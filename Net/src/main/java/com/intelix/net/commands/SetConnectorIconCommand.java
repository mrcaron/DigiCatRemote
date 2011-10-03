package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.SequencePayload;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class SetConnectorIconCommand extends Command
{
    public SetConnectorIconCommand(int number, int iconNumber, boolean isInput) {
        setClassNo(1);
        setIdNo( isInput ? 3 : 9);
        SequencePayload p = new SequencePayload(2);
        p.add(number);
        p.add(iconNumber);
        setPayload(p);
    }
}
