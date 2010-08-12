package com.intelix.digihdmi.model;

import java.util.LinkedList;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class ConnectionListenerSupport {
    LinkedList<ConnectionListener> listeners = new LinkedList<ConnectionListener>();

    public void addConnectionListener(ConnectionListener c)
    {
        listeners.add(c);
    }
    public void removeConnectionListener(ConnectionListener c)
    {
        listeners.remove(c);
    }

    public void fireDisconnect()
    {
        for(ConnectionListener l : listeners)
        {
            l.onDisconnect();
        }
    }

    public void fireConnect()
    {
        for(ConnectionListener l : listeners)
        {
            l.onConnect();
        }
    }
}
