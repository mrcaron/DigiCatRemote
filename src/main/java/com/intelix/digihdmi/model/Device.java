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
    private static boolean DELAY = false;
    private static int MAX_INPUTS = 0;
    private static int MAX_OUTPUTS = 0;
    private static int MAX_PRESETS = 0;

    private static ResourceBundle getConfiguration() {
        if (config == null) {
            config = ResourceBundle.getBundle("Device");
        }
        return config;
    }

    public Device() {
        connected = false;
        connection = new IPConnection();
        try {
            connection.setIpAddr(getConfiguration().getString("ipAddr"));
            connection.setPort(Integer.parseInt(getConfiguration().getString("port")));
            MAX_INPUTS = Integer.parseInt(getConfiguration().getString("MAX_INPUTS"));
            MAX_OUTPUTS = Integer.parseInt(getConfiguration().getString("MAX_OUTPUTS"));
            MAX_PRESETS = Integer.parseInt(getConfiguration().getString("MAX_PRESETS"));
        } catch (NullPointerException ex) {
            connection = null;
        } catch (MissingResourceException ex) {
            connection = null;
        }

        inputs = new ArrayList();
        outputs = new ArrayList();

        // MRC - This initialization assumes that # inputs == # outputs
        for (int i = 0; i < MAX_INPUTS; ++i) {
            inputs.add(new Connector("INPUT_" + (i + 1), "", i + 1));

            outputs.add(new Connector("OUTPUT_" + (i + 1), "", i + 1));

            connections.put(Integer.valueOf(i), Integer.valueOf(i));
        }

        for (int i = 0; i < MAX_PRESETS; i++) {
            presets.add(new Preset((i + 1) + " - Test", i));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (connected) {
            disconnect();
        }
    }

    public void connect()
            throws IOException {
        if (connection != null) {
            connection.connect();
            connected = true;
        } else {
            throw new IOException("Device can't be found. Check your device's configuration.");
        }
    }

    public void disconnect() throws IOException {
        if (connection != null) {
            connection.disconnect();
            connected = false;
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public Connector getInputForSelectedOutput() {
        return (Connector) inputs.get(((Integer) connections.get(Integer.valueOf(selectedOutput))).intValue());
    }

    public void makeConnection() {
        if ((connected) && (((selectedInput < 0) || (selectedOutput < 0)))) {
            return;
        }
        connections.put(Integer.valueOf(selectedOutput), Integer.valueOf(selectedInput));
    }

    public Enumeration<Preset> getPresets() {
        return new Enumeration() {

            int index;

            @Override
            public boolean hasMoreElements() {
                return index < Device.this.presets.size();
            }

            @Override
            public Preset nextElement() {
                return (Preset) Device.this.presets.get(index++);
            }
        };
    }

    public Enumeration<Connector> getInputs() {
        return new ConnectorEnumeration(inputs) {

            @Override
            public Command getNameLookupCommand(int index) {
                return new GetInputNameCommand(index);
            }
        };
    }

    public Enumeration<Connector> getOutputs() {
        return new ConnectorEnumeration(outputs) {

            @Override
            public Command getNameLookupCommand(int index) {
                return new GetOutputNameCommand(index);
            }
        };
    }

    public void setSelectedOutput(int selectedOutput) {
        selectedOutput = selectedOutput;
    }

    public Connector getSelectedOutput() {
        return (Connector) outputs.get(selectedOutput);
    }

    public void setSelectedInput(int selectedInput) {
        selectedInput = selectedInput;
    }

    public Connector getSelectedInput() {
        return (Connector) inputs.get(selectedInput);
    }

    abstract class ConnectorEnumeration
            implements Enumeration<Connector> {

        int index = 0;
        List<Connector> list = null;

        public ConnectorEnumeration() {
        }

        public ConnectorEnumeration(List<Connector> l) {
            list = l;
        }

        @Override
        public boolean hasMoreElements() {
            if (Device.this.connected) {
                return index < 8;
            }
            return index < list.size();
        }

        @Override
        public Connector nextElement() {
            if (Device.this.connected) {
                try {
                    Device.this.connection.write(getNameLookupCommand(index + 1));

                    Command c = Device.this.connection.readOne();
                    if (c.getPayload() instanceof ConnectorPayload) {
                        ConnectorPayload p = (ConnectorPayload) c.getPayload();
                        String name = p.getData();
                        list.add(index, new Connector(name, "", index + 1));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return (Connector) list.get(index++);
        }

        public abstract Command getNameLookupCommand(int paramInt);
    }
}
