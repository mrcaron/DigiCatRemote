/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.exceptions;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class UnidentifiedMessageException extends Exception {
    public UnidentifiedMessageException() { super(); }
    public UnidentifiedMessageException(String msg) { super(msg); }
    public UnidentifiedMessageException(Throwable t) { super(t); }
    public UnidentifiedMessageException(String msg, Throwable t) { super(msg, t); }
}
