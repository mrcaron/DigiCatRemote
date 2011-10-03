/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.commands;

/**
 *
 * @author mcaron
 */
public class SetUnlockPasswordCommand extends SetPasswordCommand {

    public SetUnlockPasswordCommand(String password) {
        super(password, 8, "MAX_PASS_LENGTH");
    }
}
