package com.intelix.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.logging.Logger;

public class IPConnection extends Connection {

    static final int DEFAULT_PORT = 8001;
    static final String DEFAULT_SVR = "192.168.2.189";
    static final int DEFAULT_IO_TIMEOUT = 2000;
    
    Socket sock = null;
    private String ipAddr;
    private int port;
    private OutputStream outStream;
    private InputStream inStream;
    private int ioTimeout = DEFAULT_IO_TIMEOUT;

    @Override
    public String toString() {
        return "IPConnection(" + this.ipAddr + ":" + this.port + ")";
    }

    public IPConnection() {
        super();
    }

    public IPConnection(String ipAddr, int port) throws IOException {
        this(ipAddr,port,false);
    }

    public IPConnection(String ipAddr, int port, boolean connect)
    throws IOException
    {
        this(ipAddr, port, connect, DEFAULT_IO_TIMEOUT);
    }

    public IPConnection(String ipAddr, int port, boolean connect, int ioTimeout)
    throws IOException
    {
        super();
        this.ipAddr = ipAddr;
        this.port = port;
        this.ioTimeout = ioTimeout;
        if (connect) { connect(); }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        if ((this.sock != null) && (!this.sock.isClosed())) {
            this.sock.close();
        }
    }

    public String getIpAddr() {
        return this.ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        if (this.connected) {
            try { disconnect(); } catch (Exception ex) { /* Ignore */ }
        }
        this.ipAddr = ipAddr;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        if (this.connected) {
            try { disconnect(); } catch (Exception ex) { /* Ignore */ }
        }
        this.port = port;
    }

    public void setIoTimeout(int newTimeoutInMillis)
    {
        ioTimeout = newTimeoutInMillis;
    }

    @Override
    public InputStream getInStream() {
        Logger.getLogger(getClass().getName()).finer("NET:   > Returning IO IN Stream for IP Connection.");
        return this.inStream;
    }

    @Override
    public OutputStream getOutStream() {
        Logger.getLogger(getClass().getName()).finer("NET:   > Returning IO OUT Stream for IP Connection.");
        return this.outStream;
    }

    @Override
    public void connect() throws IOException {
        this.sock = new Socket();
        this.sock.setSoTimeout(ioTimeout);

        this.sock.setTcpNoDelay(true);
        this.sock.setSendBufferSize(1024);
        this.sock.setReceiveBufferSize(1024);
        this.sock.connect(new InetSocketAddress(this.ipAddr, this.port), ioTimeout);

        this.connected = this.sock.isConnected() && !this.sock.isClosed();
        if (!this.connected) {
            return;
        }
        this.outStream = new BufferedOutputStream(this.sock.getOutputStream(), MAX_BUFFER_SIZE);
        this.inStream = new BufferedInputStream(this.sock.getInputStream(), MAX_BUFFER_SIZE);
    }

    @Override
    public void disconnect() {
        if (sock == null)
            return;
        
        if (isConnected())
        {
            try{
                getOutStream().flush();
                getOutStream().close();
                getInStream().close();
            } catch (IOException ex) { } finally {
                try {
                    this.sock.close();
                } catch (IOException ex) {}
            }
        }
    }

    @Override
    public boolean isConnected() {
        return this.sock != null && this.sock.isConnected() && !this.sock.isClosed();
    }
}

