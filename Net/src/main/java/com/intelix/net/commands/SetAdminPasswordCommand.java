/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.commands;

/**
 *
 * @author mcaron
 */
public class SetAdminPasswordCommand extends SetPasswordCommand {

    public SetAdminPasswordCommand(String password) {
        super(password, 10, "MAX_PASS_LENGTH");
    }

}
