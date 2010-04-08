package com.intelix.digihdmi.model;

import com.intelix.net.Command;
import com.intelix.net.GetInputNameCommand;
import com.intelix.net.GetOutputNameCommand;
import com.intelix.net.IPConnection;
import com.intelix.net.payload.ConnectorPayload;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Device {

    private int selectedOutput;
    private int selectedInput;
    private ArrayList<Connector> inputs = new ArrayList();
    private ArrayList<Connector> outputs = new ArrayList();
    private ArrayList<Preset> presets = new ArrayList();
    private HashMap<Integer, Integer> connections = new HashMap();
    private static ResourceBundle config;
    private boolean connected;
    private IPConnection connection;
    private static final int MAX = 10;
    private static final boolean DELAY = false;
    private static final int MAX_INPUTS = 8;
    private static final int MAX_OUTPUTS = 8;

    private static ResourceBundle getConfiguration() {
        if (config == null) {
            config = ResourceBundle.getBundle("Device");
        }
        return config;
    }

    public Device() {
        this.connected = false;
        this.connection = new IPConnection();
        try {
            this.connection.setIpAddr(getConfiguration().getString("ipAddr"));
            this.connection.setPort(Integer.parseInt(getConfiguration().getString("port")));
        } catch (NullPointerException ex) {
            this.connection = null;
        } catch (MissingResourceException ex) {
            this.connection = null;
        }

        this.inputs = new ArrayList();
        this.outputs = new ArrayList();

        for (int i = 0; i < 10; ++i) {
            this.inputs.add(new Connector("INPUT_" + (i + 1), ""));
            this.outputs.add(new Connector("OUTPUT_" + (i + 1), ""));

            this.connections.put(Integer.valueOf(i), Integer.valueOf(i));

            this.presets.add(new Preset(i + " - Test", i));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (this.connected) {
            disconnect();
        }
    }

    public void connect()
            throws IOException {
        if (this.connection != null) {
            this.connection.connect();
            this.connected = true;
        } else {
            throw new IOException("Device can't be found. Check your device's configuration.");
        }
    }

    public void disconnect() throws IOException {
        if (this.connection != null) {
            this.connection.disconnect();
            this.connected = false;
        }
    }

    public boolean isConnected() {
        return this.connected;
    }

    public Connector getInputForSelectedOutput() {
        return (Connector) this.inputs.get(((Integer) this.connections.get(Integer.valueOf(this.selectedOutput))).intValue());
    }

    public void makeConnection() {
        if ((this.connected) && (((this.selectedInput < 0) || (this.selectedOutput < 0)))) {
            return;
        }
        this.connections.put(Integer.valueOf(this.selectedOutput), Integer.valueOf(this.selectedInput));
    }

    public Enumeration<Preset> getPresets() {
        return new Enumeration() {

            int index;

            @Override
            public boolean hasMoreElements() {
                return this.index < Device.this.presets.size();
            }

            @Override
            public Preset nextElement() {
                return (Preset) Device.this.presets.get(this.index++);
            }
        };
    }

    public Enumeration<Connector> getInputs() {
        return new ConnectorEnumeration(this.inputs) {

            @Override
            public Command getNameLookupCommand(int index) {
                return new GetInputNameCommand(index);
            }
        };
    }

    public Enumeration<Connector> getOutputs() {
        return new ConnectorEnumeration(this.outputs) {

            @Override
            public Command getNameLookupCommand(int index) {
                return new GetOutputNameCommand(index);
            }
        };
    }

    public void setSelectedOutput(int selectedOutput) {
        this.selectedOutput = selectedOutput;
    }

    public Connector getSelectedOutput() {
        return (Connector) this.outputs.get(this.selectedOutput);
    }

    public void setSelectedInput(int selectedInput) {
        this.selectedInput = selectedInput;
    }

    public Connector getSelectedInput() {
        return (Connector) this.inputs.get(this.selectedInput);
    }

    abstract class ConnectorEnumeration
            implements Enumeration<Connector> {

        int index = 0;
        List<Connector> list = null;

        public ConnectorEnumeration() {
        }

        public ConnectorEnumeration(List<Connector> l)
        {
            this.list = l;
        }

        @Override
        public boolean hasMoreElements() {
            if (Device.this.connected) {
                return this.index < 8;
            }
            return this.index < this.list.size();
        }

        @Override
        public Connector nextElement() {
            if (Device.this.connected) {
                try {
                    Device.this.connection.write(getNameLookupCommand(this.index + 1));

                    Command c = Device.this.connection.readOne();
                    if (c.getPayload() instanceof ConnectorPayload) {
                        ConnectorPayload p = (ConnectorPayload) c.getPayload();
                        String name = p.getData();
                        this.list.add(this.index, new Connector(name, ""));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return (Connector) this.list.get(this.index++);
        }

        public abstract Command getNameLookupCommand(int paramInt);
    }
}

/* Location:           /Users/developer/Downloads/dist/DigiHdmiApp-1.0.0-SNAPSHOT.jar
 * Qualified Name:     com.intelix.hdmimodel.Device
 * JD-Core Version:    0.5.4
 */
