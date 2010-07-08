package com.intelix.digihdmi.model;

import com.intelix.net.Command;
import com.intelix.net.Connection;
import com.intelix.net.GetAllCrosspointsCommand;
import com.intelix.net.GetCrosspointCommand;
import com.intelix.net.GetInputNameCommand;
import com.intelix.net.GetOutputNameCommand;
import com.intelix.net.GetPresetCommand;
import com.intelix.net.GetPresetNameCommand;
import com.intelix.net.IPConnection;
import com.intelix.net.SetAdminPasswordCommand;
import com.intelix.net.SetCrosspointCommand;
import com.intelix.net.SetPresetCommand;
import com.intelix.net.SetPresetNameCommand;
import com.intelix.net.SetUnlockPasswordCommand;
import com.intelix.net.ToggleLockCommand;
import com.intelix.net.payload.ConnectorPayload;
import com.intelix.net.payload.PairSequencePayload;
import com.intelix.net.payload.PresetNamePayload;
import com.intelix.net.payload.PresetReportPayload;
import com.intelix.net.payload.SequencePayload;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Device has a set of inputs and outputs as well as an active connection matrix.
 * Inputs and outputs are 1-based on the device itself! The model assumes a zero-based
 * system, and does the translation to the 1-based system internally. That is, everything
 * working with the device should assume that things are one-based; for example,
 * setting the selected output to 1 if the first output is selected. It would never
 * be set to 0.
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class Device {

    private int selectedOutput;
    private int selectedInput;
    private ArrayList<Connector> inputs = new ArrayList();
    private ArrayList<Connector> outputs = new ArrayList();
    private ArrayList<Preset> presets = new ArrayList();
    private HashMap<Integer, Integer> cxnMatrix = new HashMap();  /* KEY=Output,VALUE=Input */
    private static ResourceBundle config;
    private boolean connected;
    private IPConnection connection;
    private static int MAX_INPUTS = 0;
    private static int MAX_OUTPUTS = 0;
    private static int MAX_PRESETS = 0;

    private static int MAX_TRIES = 3;

    private boolean locked = false;

    private String unlockPassword = "abcd";
    private String adminPassword = "abcd";

    // flag used to determine if we need to visit the device for input/output information
    private boolean resetInput = true;
    private boolean resetOutput = true;
    private boolean resetPresets = true;
    private boolean resetXP = true;

    // DEBUG PROPERTIES
    private static int DELAY = 0;

    //------------------------------------------------------------------------
    private static ResourceBundle getConfiguration() {
        if (config == null) {
            config = ResourceBundle.getBundle("Device");
        }
        return config;
    }
    private int MAX_PRESET_NAME_LENGTH = 0;
    private int MAX_ADMIN_PASS_LENGTH = 0;
    private int MAX_LOCK_PASS_LENGTH = 0;

    //------------------------------------------------------------------------
    /* Initialize the Device */
    public Device() {
        connected = false;
        connection = new IPConnection();

        try {
            String delay = getConfiguration().getString("delay");
            DELAY = Integer.parseInt(delay);
        } catch (Exception e)
        {
            // IGNORE - We'll just have a 0 delay then, and a 4 length password
        }

        try {
            MAX_PRESET_NAME_LENGTH = Integer.parseInt(
                getConfiguration().getString("MAX_PRESET_NAME_LENGTH"));
            MAX_ADMIN_PASS_LENGTH = Integer.parseInt(
                getConfiguration().getString("MAX_ADMIN_PASS_LENGTH"));
            MAX_LOCK_PASS_LENGTH = Integer.parseInt(
                getConfiguration().getString("MAX_LOCK_PASS_LENGTH"));
        } catch (Exception e)
        {
            // IGNORE
        }

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
            inputs.add(new Connector("INPUT_" + (i + 1), "", i + 1));            // Connectors are 1-based for their index
            outputs.add(new Connector("OUTPUT_" + (i + 1), "", i + 1));          
            cxnMatrix.put(i,0);
        }

        for (int i = 0; i < MAX_PRESETS; i++) {
            Preset p = new Preset((i + 1) + " - Test", i + 1);
            for(int j=0; j < MAX_INPUTS; ++j)
            {
                p.makeConnection((j * i) % MAX_INPUTS, (j + i) % MAX_INPUTS);
            }
            presets.add(p);
        }
    }

    //------------------------------------------------------------------------
    @Override
    protected void finalize() throws Throwable {
    /* Kill the connection on finailzation in the case that it's still up. */
        super.finalize();
        if (connected) {
            disconnect();
        }
    }

    //------------------------------------------------------------------------
    /* connect to the actual HDMI devine if we can. */
    public void connect()
            throws IOException {
        if (connection != null) {
            connection.connect();
            connected = true;
            setFullReset(true);
        } else {
            throw new IOException("Device can't be found. Check your device's configuration.");
        }
    }

    //------------------------------------------------------------------------
    /* Disconnect from the device */
    public void disconnect() throws IOException {
        if (connection != null) {
            connection.disconnect();
            connected = false;
        }
    }

    //------------------------------------------------------------------------
    public boolean isConnected() {
        return connected;
    }

    //------------------------------------------------------------------------
    public Connector getInputForSelectedOutput() {
        return getInputForSelectedOutput(true);
    }
    
    public Connector getInputForSelectedOutput(boolean live) {
        if (connected && live)
        {
            try {
                Command c = new GetCrosspointCommand(selectedOutput+1);
                connection.write(c);

                c = connection.readOne();
                if (c.getPayload() instanceof PairSequencePayload) {
                    PairSequencePayload p = (PairSequencePayload) c.getPayload();
                    selectedInput = p.get(selectedOutput + 1) - 1;

                    cxnMatrix.put(selectedOutput, selectedInput);
                }
            } catch (Exception ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (Connector) inputs.get(cxnMatrix.get(selectedOutput));
    }

    //------------------------------------------------------------------------
    public boolean makeConnection() {
        if (connected) {
            if ((selectedInput < 0) || (selectedOutput < 0)) {
                return false;
            }
            Command c = new SetCrosspointCommand(selectedInput+1, selectedOutput+1);
            boolean obtained = false;
            try {
                connection.write(c);
                Thread.sleep(1500);
                c = connection.readOne();
                if (c.getPayload() instanceof PairSequencePayload) {
                    PairSequencePayload p = (PairSequencePayload) c.getPayload();
                    selectedInput = p.get(selectedOutput + 1) - 1;
                    //cxnMatrix.put(selectedOutput, selectedInput);
                }
            } catch (Exception ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        cxnMatrix.put(selectedOutput, selectedInput);
        return true;
    }

    //------------------------------------------------------------------------
    public Enumeration<Preset> getPresets() {
        return new Enumeration() {
            int index = 0;

            @Override
            public boolean hasMoreElements() {
                if (connected && resetPresets)
                {
                    boolean r = index < MAX_PRESETS;
                    if (!r) { 
                        resetPresets = false;
                    }
                    return r;
                }
                return index < presets.size();
            }

            @Override
            public Preset nextElement() {
                if (connected && resetPresets)
                {
                    boolean obtained = false;
                    int i = 0;
                    while(!obtained && i < MAX_TRIES)
                    {
                        try {
                            connection.write(new GetPresetNameCommand(index + 1));
                            //Thread.sleep(1000);
                            Command c = connection.readOne();
                            if (c.getPayload() instanceof PresetNamePayload) {
                                PresetNamePayload p = (PresetNamePayload) c.getPayload();
                                String name = p.getData();
                                presets.set(index, new Preset(name, index + 1));
                            }
                            obtained = true;
                        } catch (IOException ex) {
                            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (DELAY > 0) {
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return (Preset) presets.get(index++);
            }
        };
    }

    //------------------------------------------------------------------------
    
    public Enumeration<Connector> getInputs() {
        return getInputs(false);
    }
    public Enumeration<Connector> getInputs(boolean live) {
        return new ConnectorEnumeration(inputs, live) {

            @Override
            public boolean isReset() { return resetInput; }

            @Override
            public void setReset(boolean r) {
                resetInput = r;
            }

            @Override
            public Command getNameLookupCommand(int index) {
                return new GetInputNameCommand(index);
            }

            @Override
            public int getMax() {
                return MAX_INPUTS;
            }
        };
    }

    //------------------------------------------------------------------------
    public Enumeration<Connector> getOutputs() {
        return getOutputs(false);
    }

    public Enumeration<Connector> getOutputs(boolean live) {
        return new ConnectorEnumeration(outputs, live) {
            @Override
            public boolean isReset() { 
                return resetOutput;
            }

            @Override
            public void setReset(boolean r) {
                resetOutput = r;
            }

            @Override
            public Command getNameLookupCommand(int index) {
                return new GetOutputNameCommand(index);
            }

            @Override
            public int getMax() {
                return MAX_OUTPUTS;
            }

        };
    }

    //------------------------------------------------------------------------
    public HashMap<Integer,Integer> getCrossPoints() {
        return getCrossPoints(false);
    }
    public HashMap<Integer,Integer> getCrossPoints(boolean live)
    {
        if (connected && resetXP)
        {
            try {
                connection.write(new GetAllCrosspointsCommand());
                Command c = connection.readOne();
                if (c.getPayload() instanceof SequencePayload)
                {
                    SequencePayload p = (SequencePayload)c.getPayload();
                    for(int i=0; i<p.size(); i++)
                    {
                        cxnMatrix.put(i/*Output*/,p.get(i)-1/*Input*/);
                    }
                }
                resetXP = false;
            } catch (Exception ex)
            {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cxnMatrix;
    }

    //------------------------------------------------------------------------
    public void setFullReset(boolean reset) {
        resetInput = resetOutput = resetPresets = resetXP = reset;
    }
    //------------------------------------------------------------------------
    public void setSelectedOutput(int selectedOutput) {
        this.selectedOutput = selectedOutput-1;
    }

    //------------------------------------------------------------------------
    public Connector getSelectedOutput() {
        return (Connector) outputs.get(selectedOutput);
    }

    //------------------------------------------------------------------------
    public void setSelectedInput(int selectedInput) {
        this.selectedInput = selectedInput-1;
    }

    //------------------------------------------------------------------------
    public Connector getSelectedInput() {
        return (Connector) inputs.get(selectedInput);
    }

    //------------------------------------------------------------------------
    public void loadPreset(int number)
    {
        // get the preset from the array
        Preset p = presets.get(number - 1);

        if (connected)
        {
            try {
                connection.write(new GetPresetCommand(number));
                p = readPresetReport(p.getName(), number);
            } catch (Exception ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // set the connection matrix from this
        cxnMatrix = p.getConnections();
    }

    //------------------------------------------------------------------------
    public void savePreset(int number, String name)
    {
        // Get current preset
        Preset newPreset = new Preset(name, number);

        // Setup the set preset command while filling up new preset
        SetPresetCommand saveCmd = new SetPresetCommand(number);
        for(int i=0; i<cxnMatrix.size(); i++)
        {
            // add the input to the payload in the correct slot
            SequencePayload p = (SequencePayload)saveCmd.getPayload();
            p.add(cxnMatrix.get(i) + 1);

            // add the input and output to the new internal Preset
            newPreset.makeConnection(cxnMatrix.get(i), i);
        }

        if (connected)
        {
            try {
                // Set the name of a preset
                SetPresetNameCommand cmd = new SetPresetNameCommand(number, name);

                // Write a Set Preset Command
                connection.write(saveCmd);
                newPreset = readPresetReport(name, number);
            } catch (Exception ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        presets.set(number-1, newPreset);
    }

    //------------------------------------------------------------------------
    private Preset readPresetReport(String name, int number) throws IOException {
        // Read a Preset Report Command
        Preset p = new Preset(name, number);
        Command c = connection.readOne();
        if (c.getPayload() instanceof PresetReportPayload) {
            PresetReportPayload pld = (PresetReportPayload) c.getPayload();
            for (int i = 1; i <= MAX_OUTPUTS; i++) {
                p.makeConnection(pld.getInputForOutput(i), i);
            }
        }
        return p;
    }

    //------------------------------------------------------------------------
    public int getNumOutputs() {
        return MAX_OUTPUTS;
    }
    public int getNumInputs() {
        return MAX_INPUTS;
    }
    public int getNumPresets() {
        return MAX_PRESETS;
    }
    public int getAdminPassLength() {
        return MAX_ADMIN_PASS_LENGTH;
    }
    public int getLockPassLength() {
        return MAX_LOCK_PASS_LENGTH;
    }
    public int getPresetNameLength() {
        return MAX_PRESET_NAME_LENGTH;
    }
    public Connection getConnection() {
        return connection;
    }

    //------------------------------------------------------------------------
    public byte[] getPasswordHash(String pwd) {
        MessageDigest md;
        byte[] digested = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            digested = md.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
        }

        return digested;
    }

    //------------------------------------------------------------------------
    // Password operations
    // Network library just truncates password if it's too long.
    private String setPassword(Command cmdIn, String pwd)
    {
        String newPwd = pwd;
        if (connected)
        {
            try {
                connection.write(cmdIn);
                //Thread.sleep(1500);
                Command cmdOut = connection.readOne();
                if (cmdOut.getPayload() instanceof SequencePayload)
                {
                    SequencePayload p = (SequencePayload)cmdOut.getPayload();
                    int status = p.get(0);
                    if (status != 0)
                    {
                        newPwd = this.adminPassword;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            } /*catch (InterruptedException ex)
            {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }

        return newPwd;
    }

    public void setAdminPassword(String pwd)
    {
        this.adminPassword = setPassword(new SetAdminPasswordCommand(pwd), pwd);
    }

    public void setUnlockPassword(String pwd) {
        this.unlockPassword = setPassword(new SetUnlockPasswordCommand(pwd), pwd);
    }

    //------------------------------------------------------------------------
    // Lock functionality
    //------------------------------------------------------------------------
    // Network library just truncates password if it's too long.
    public boolean unlock(String password) {
        boolean success = false;
        
        if (connected) {
            try {
                connection.write(new ToggleLockCommand(password));
                Command c = connection.readOne();
                if (c.getPayload() instanceof SequencePayload)
                {
                    SequencePayload p = (SequencePayload)c.getPayload();
                    int status = p.get(0);
                    locked = status == 0;
                }
            } catch (Exception ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // get actual pass digest
            byte[] passHash = getPasswordHash(unlockPassword);

            // digest submitted password
            MessageDigest md;
            byte[] submittedDigest = null;
            try {
                md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                submittedDigest = md.digest();
            } catch (NoSuchAlgorithmException ex)
            {
                // bah!
            }

            // check equality
            if (passHash != null && submittedDigest != null &&
                java.util.Arrays.equals(passHash, submittedDigest))
                locked = false;
        }

        return !locked;
    }

    public void lock() {
        // send code to machine to lock itself.
        if (connected) {
            try {
                connection.write(new ToggleLockCommand());
                Command c = connection.readOne();
                if (c.getPayload() instanceof SequencePayload)
                {
                    SequencePayload p = (SequencePayload)c.getPayload();
                    int status = p.get(0);
                    locked = status == 0;
                }
            } catch (Exception ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            locked = true;
        }
    }

    public boolean isLocked() {
        return locked;
    }

    //------------------------------------------------------------------------
    abstract class ConnectorEnumeration
            implements Enumeration<Connector> {

        int index = 0;
        List<Connector> list = null;
        boolean live;
        
        public ConnectorEnumeration() {
        }

        public ConnectorEnumeration(List<Connector> l) {
            this(l,false);
        }

        public ConnectorEnumeration(List<Connector> l, boolean live)
        {
            list = l;
            this.live = live;
        }

        @Override
        public boolean hasMoreElements() {
            if (connected && (live || isReset())) {
                boolean r = index < getMax();
                // We do not need to fetch from the device any longer
                if (!r) { 
                    setReset(false);
                }
                return r;
            }
            return index < list.size();
        }

        @Override
        public Connector nextElement() {
            if (connected && (live || isReset())) {
                try {
                    connection.write(getNameLookupCommand(index + 1));

                    Command c = connection.readOne();
                    if (c.getPayload() instanceof ConnectorPayload) {
                        ConnectorPayload p = (ConnectorPayload) c.getPayload();
                        String name = p.getData();

                        Connector ctr = new Connector(name, "", index + 1);
                        /*try {*/
                            list.set(index, ctr);
                        /*}
                        catch (IndexOutOfBoundsException ex) {
                            list.add(index, ctr);
                        }*/
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (DELAY > 0) {
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return (Connector) list.get(index++);
        }

        public abstract boolean isReset();
        public abstract void setReset(boolean r);
        public abstract Command getNameLookupCommand(int paramInt);
        public abstract int getMax();
    }
}
