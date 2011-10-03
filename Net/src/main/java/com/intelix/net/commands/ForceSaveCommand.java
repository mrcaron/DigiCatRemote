/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.commands;

import com.intelix.net.Command;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class ForceSaveCommand extends Command {
    public ForceSaveCommand() {
        setClassNo(00);
        setIdNo(21);
    }
}
