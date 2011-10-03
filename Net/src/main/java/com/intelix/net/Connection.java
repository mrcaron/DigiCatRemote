package com.intelix.net;

import com.intelix.net.exceptions.InsufficientDataException;
import com.intelix.net.exceptions.UnidentifiedMessageException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Connection {

    static final int MAX_BUFFER_SIZE = 1024 /* increased from 70 */;
    private byte[] bufferIn;
    private byte[] bufferOut;
    private int buffLimit = 0;
    protected boolean connected = false;

    public Connection() {
        init();
    }

    public final void init()
    {
        bufferIn = new byte[MAX_BUFFER_SIZE];
        flushBufferIn();
    }

    public abstract void connect() throws Exception;

    public abstract InputStream getInStream();

    public abstract OutputStream getOutStream();

    public abstract void disconnect() throws IOException;

    public boolean isConnected() {
        return this.connected;
    }

    private void flushBufferIn() {
        this.buffLimit = 0;
        Arrays.fill(this.bufferIn, (byte)0);
    }

    public void write(Command cmd) throws IOException {
        try {
            try {
                bufferOut = cmd.encode();
            } catch (IndexOutOfBoundsException ex)
            {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                        "INDEX OUT OF BOUNDS WHILE ENCODING MESSAGE!", ex);
            }

            StringBuffer sBuff = new StringBuffer(bufferOut.length + 3);
            for(byte b : bufferOut)
            {
                sBuff.append(Integer.toHexString(b & 0xff));
                sBuff.append(',');
            }
            Logger.getLogger(getClass().getName()).log(Level.FINE, ">>>>WRITE: [{0}]", sBuff);

            clearInput(500);
            getOutStream().write(bufferOut);
            getOutStream().flush();

            //Logger.getLogger(getClass().getName()).fine("done.");
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                        "UNEXPECTED EXCEPTION!", ex);
        }
    }

    public void write(List<Command> commands) throws IOException {
        /* maybe try writing all the commands at once to improve perf. */
        for (Command c : commands) {
            write(c);
        }
    }

    public void clearInput(int waitTimeInMillisec) throws IOException
    {
        InputStream in = getInStream();
        while(in.available() > 0)
        {
            int avl = in.available();
            Logger.getLogger(getClass().getName()).log(Level.FINE,"Clearing input buffer. Bytes available: {0}.",avl);
            long skipped = in.skip(avl);
            Logger.getLogger(getClass().getName()).log(Level.FINE, "Skipped {0} bytes", skipped);
            try {
                Logger.getLogger(getClass().getName()).log(Level.FINE,"Waiting {0} before another clearing.",waitTimeInMillisec);
                Thread.sleep(waitTimeInMillisec);
            } catch (InterruptedException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Logger.getLogger(getClass().getName()).log(Level.FINE,"Finished clearing input. Input buffer bytes available: {0}", in.available());
    }

    public Command readOne()
            throws IOException, UnidentifiedMessageException {
        Command c = null;

        Logger.getLogger(getClass().getName()).finer("Setting up reader");
        InputStream in = getInStream();
        int bytesRead = 0;
        flushBufferIn();
        //boolean started = false;
        //byte[] backupBuff = null;

        Logger.getLogger(getClass().getName()).finer("About to start reading");
        int MAX_TRIES = 3;
        int tries = 1;
        while (/*((*/c == null && tries <= MAX_TRIES /*) && (bytesRead > 0)) || ( (!started) && tries <= MAX_TRIES) */){
            try {
                //Logger.getLogger(getClass().getName()).fine("<<< READ: Try(" + (tries++) + ")");

                bytesRead = in.read(this.bufferIn);  // blocks until there's something to read

                StringBuffer sBuff = new StringBuffer(bytesRead+5);
                for(byte b : bufferIn)
                {
                    sBuff.append(Integer.toHexString(b & 0xff));
                    sBuff.append(',');
                }
                Logger.getLogger(getClass().getName()).log(Level.FINE, "<<<<READ(T{0}): [{1}]", new Object[]{tries, sBuff});
                    //"<<< READ: read " + bytesRead + " bytes, [" + sBuff.toString() + "]");
                if (bytesRead > 0)
                {
                    //Logger.getLogger(getClass().getName()).FINE("started processing bytes");
                    //int position = 0;
                    //while (position < bytesRead) {
                      //  Logger.getLogger(getClass().getName()).fine(">>> NET(ReadOne): Cycling inner loop");
                      //  Logger.getLogger(getClass().getName()).fine(">>> NET(ReadOne): position:" + position);
                      //  try {
                      //      byte[] temp = null;
                      //      if (backupBuff != null) {
                      //          Logger.getLogger(getClass().getName()).fine(">>> NET(ReadOne): copying backup buffer");
                      //          temp = Arrays.copyOf(backupBuff, backupBuff.length + this.bufferIn.length);
                      //          System.arraycopy(this.bufferIn, 0, temp, backupBuff.length, this.bufferIn.length);
                      //      } else {
                      //          temp = this.bufferIn;
                      //      }
                      //      Logger.getLogger(getClass().getName()).fine(">>> NET(ReadOne): backup buffer size:" + temp.length);
                      //      backupBuff = null;
                      try {
                        //Logger.getLogger(getClass().getName()).info("Decoding");
                        c = new Command();
                        c.decode(bufferIn, 0);
                        //Logger.getLogger(getClass().getName()).FINE("Decoded, position="+position);
                            //Logger.getLogger(getClass().getName()).fine(">>> NET(ReadOne): position after decode:" + position);
                      } catch (InsufficientDataException ex) {
                        Logger.getLogger(getClass().getName()).fine(">>> NET(ReadOne): Have to cycle again, didn't get enough data.");
                        //backupBuff = new byte[bytesRead - position];
                        //System.arraycopy(this.bufferIn, position, backupBuff, 0, backupBuff.length);
                        c = null;
                      }
                    //}
                }
            }
            catch (SocketTimeoutException ex)
            {
                Logger.getLogger(getClass().getName()).fine("Read timed out. Try to read again.");
                tries++;
            }
        }
        if (tries >= MAX_TRIES)
        {
            StringBuffer sBuff = new StringBuffer(bufferOut.length + 3);
            for(byte b : bufferOut)
            {
                sBuff.append(Integer.toHexString(b & 0xff));
                sBuff.append(',');
            }
            throw new IOException("Wrote ("+sBuff+"). But didn't hear back from the device.");
        }

        return c;
    }
}
