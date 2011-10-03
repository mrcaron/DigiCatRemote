package com.intelix.net.commands;

import com.intelix.net.Command;
import com.intelix.net.payload.StringPayload;
import java.util.ResourceBundle;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class ToggleLockCommand extends Command {

    private static int MAX_PASS_LENGTH = -1;

    public ToggleLockCommand() {
        super();
        setClassNo(0);
        setIdNo(12);
    }

    public ToggleLockCommand(String password)
    {
        this();
        setIdNo(2);

        ResourceBundle config = ResourceBundle.getBundle("Device");
        if (MAX_PASS_LENGTH < 0)
            MAX_PASS_LENGTH = Integer.parseInt(
                config.getString("MAX_PASS_LENGTH"));
        StringPayload p = new StringPayload(MAX_PASS_LENGTH, password);
        setPayload(p);
    }
}
