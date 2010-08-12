package com.intelix.digihdmi.util;

import com.intelix.net.Connection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author mcaron
 */
public class FakeConnection extends Connection {

    @Override
    public void connect() throws Exception {
        connected = true;
    }

    @Override
    public InputStream getInStream() {
        return null;
    }

    @Override
    public OutputStream getOutStream() {
        return null;
    }

    @Override
    public void disconnect() throws IOException {
        connected = false;
    }

}
