package com.intelix.net.commands;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class ToggleUtilityLockCommand extends ToggleLockCommand {
    public ToggleUtilityLockCommand(String password) {
        super(password);
        setIdNo(5);
    }

    public ToggleUtilityLockCommand() {
        super();
        setIdNo(13);
    }
}
