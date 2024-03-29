package com.intelix.digihdmi.model;

import com.intelix.digihdmi.util.FakeConnection;
import com.intelix.net.commands.*;
import com.intelix.net.*;
import com.intelix.net.exceptions.UnidentifiedMessageException;
import com.intelix.net.payload.*;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
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
public class Device implements PropertyChangeListener {

    @XStreamOmitField
    PropertyChangeSupport pcsupport;

    @XStreamOmitField
    private int selectedOutput;

    @XStreamOmitField
    private int selectedInput;
    private ArrayList<Connector> inputs = new ArrayList();
    private ArrayList<Connector> outputs = new ArrayList();
    private ArrayList<Preset> presets = new ArrayList();
    private HashMap<Integer, Integer> cxnMatrix = new HashMap();  /* KEY=Output,VALUE=Input */

    @XStreamOmitField
    private static Properties config;

    @XStreamOmitField
    private boolean connected;
    @XStreamOmitField
    //private IPConnection connection;
    private Connection connection;
    
    @XStreamOmitField
    private int numInputs = 0;
    @XStreamOmitField
    private int numOutputs = 0;
    @XStreamOmitField
    private int numPresets = 0;
    @XStreamOmitField
    private int maxPresetNameLength = 0;
    @XStreamOmitField
    private int maxIONameLength = 0;
    @XStreamOmitField
    private int maxPassLength = 0;
    // PropertyChangeListeners will get reports about this one

    @XStreamOmitField
    private float progress = 0f;
    private static int MAX_TRIES = 2;

    @XStreamOmitField
    private boolean locked = false;
    private String unlockPassword = "abcd";
    private String adminPassword = "abcd";
    // flag used to determine if we need to visit the device for input/output information
    @XStreamOmitField
    private boolean resetInput = true;

    @XStreamOmitField
    private boolean resetOutput = true;

    @XStreamOmitField
    private boolean resetPresets = true;

    @XStreamOmitField
    private String activeAdminPassword = "";

    @XStreamOmitField
    private boolean resetXP = true;

    @XStreamOmitField
    private boolean adminUnlocked = true;

    @XStreamOmitField
    private static URL propertiesFile;

    // DEBUG PROPERTIES
    @XStreamOmitField
    private static int DELAY = 0;

    @XStreamOmitField
    private boolean pushing = false;

    @XStreamOmitField
    private ConnectionListenerSupport cxnEvtSupport;

    @XStreamOmitField
    private boolean pulling;

    //------------------------------------------------------------------------
    public void setPushing(boolean pushing) {
        this.pushing = pushing;
    }
    public boolean isPushing()
    {
        return pushing;
    }

    //------------------------------------------------------------------------
    private static Properties getConfiguration() {
        if (config == null) {
            //config = PropertyResourceBundle.getBundle("Device");
            propertiesFile = ClassLoader.getSystemResource("Device.properties");
            config = new Properties();
            try {
                config.load(new FileInputStream(propertiesFile.getFile()));
            } catch (Exception ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, "Can't find Device.properties file! Loading a blank one.", ex);
                config = null;
            }
        }
        return config;
    }

    private static void saveConfiguration() {
        if (config != null)
        {
            try {
                OutputStream out = new FileOutputStream(propertiesFile.getFile());
                config.store(out, "---Routine Save---");
                out.close();
            } catch (Exception ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE,
                        "Error while saving Device properties file", ex);
            }
        }
    }

    //------------------------------------------------------------------------
    /* Initialize the Device */
    @SuppressWarnings("LeakingThisInConstructor")
    public Device() {
        init();

        inputs = new ArrayList();
        outputs = new ArrayList();

        // MRC - This initialization assumes that # inputs == # outputs
        for (int i = 0; i < numInputs; ++i) {

            Input inpt = new Input("I_" + (i + 1), 1, i + 1);
            inpt.addPropertyChangeListener(this);

            Output otpt = new Output("O_" + (i + 1), 1, i + 1);
            otpt.addPropertyChangeListener(this);

            inputs.add(inpt);            // Connectors are 1-based for their index
            outputs.add(otpt);
            cxnMatrix.put(i, 0);
        }

        for (int i = 0; i < numPresets; i++) {
            Preset p = new Preset("PS_" + (i + 1), i + 1);
            for (int j = 0; j < numInputs; ++j) {
                p.makeConnection((j * i) % numInputs, (j + i) % numInputs);
            }
            presets.add(p);
        }
    }

    public final void init()
    {
        cxnEvtSupport = new ConnectionListenerSupport();
        pcsupport = new PropertyChangeSupport(this);
        connected = false;
        
        if (connection != null)
        {
            connection.init();
        }

        try {
            String delay = getConfiguration().getProperty("delay");
            DELAY = Integer.parseInt(delay);
        } catch (Exception e) {
            // IGNORE - We'll just have a 0 delay then, and a 4 length password
        }

        try {
            maxPresetNameLength = Integer.parseInt(
                    getConfiguration().getProperty("MAX_PRESET_NAME_LENGTH"));
            maxPassLength = Integer.parseInt(
                    getConfiguration().getProperty("MAX_PASS_LENGTH"));
            maxIONameLength = Integer.parseInt(
                    getConfiguration().getProperty("MAX_IO_NAME_LENGTH"));
            numInputs = Integer.parseInt(getConfiguration().getProperty("MAX_INPUTS"));
            numOutputs = Integer.parseInt(getConfiguration().getProperty("MAX_OUTPUTS"));
            numPresets = Integer.parseInt(getConfiguration().getProperty("MAX_PRESETS"));

        } catch (MissingResourceException ex) {
            connection = null;
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.SEVERE,
                    "Missing Device.properties file or information in it!", ex);
        } catch (Exception e) {
            // IGNORE
        }

        if (System.getProperty("USE_FAKE_CXN") != null)
            initializeFakeConnection();
        else
            initializeIPConnection();
    }

    private void initializeFakeConnection() {
        FakeConnection c = new FakeConnection();
        this.connection = c;
    }

    private void initializeIPConnection() {
        IPConnection c = new IPConnection();

        try {
            c.setIpAddr(getConfiguration().getProperty("ipAddr"));
            c.setPort(Integer.parseInt(getConfiguration().getProperty("port")));
        } catch (NullPointerException ex) {
            c = null;
        } catch (MissingResourceException ex) {
            c = null;
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.SEVERE,
                    "Missing Device.properties file or information in it!", ex);
        }

        this.connection = c;
    }

    //------------------------------------------------------------------------
    @Override
    @SuppressWarnings("FinalizeDeclaration")
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
            throws Exception {
        if (connection != null && !connection.isConnected()) {

            // set timeout
            if (connection instanceof IPConnection)
            {
                String ioTimeout = getConfiguration().getProperty("ioTimeout");
                if (ioTimeout != null && !ioTimeout.isEmpty())
                    ((IPConnection)connection).setIoTimeout(Integer.parseInt(ioTimeout));
            }

            connection.connect();

            boolean connectedNew = connection.isConnected();
            pcsupport.firePropertyChange("connected", connected, connectedNew);
            connected = connectedNew;
            
            setFullReset(true);
        } else {
            throw new IOException("Device can't be found. Check your device's configuration.");
        }
    }

    //------------------------------------------------------------------------
    /* Disconnect from the device */
    public void disconnect() throws IOException {
        if (connection != null && connection.isConnected()) {

            cxnEvtSupport.fireDisconnect();

            if (System.getProperty("USE_FAKE_CXN") == null)
                if (adminUnlocked)
                    lockAdmin();

            connection.disconnect();

            boolean connectedNew = connection.isConnected();
            pcsupport.firePropertyChange("connected", connected, connectedNew);
            connected = connectedNew;

            // when we're disconnected, there should be no password to lock
            // or unlock the admin screens. Makes no sense.
            adminUnlocked = true;

        }
    }

    //------------------------------------------------------------------------
    public boolean isConnected() {
        return connection.isConnected();
    }

    //------------------------------------------------------------------------
    public Connector getInputForSelectedOutput() {
        return getInputForSelectedOutput(true);
    }

    public Connector getInputForSelectedOutput(boolean live) {
        if (connected && live) {
            try {
                Command c = new GetCrosspointCommand(selectedOutput + 1);
                if (deviceWriteRead(c, PairSequencePayload.class)) {
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
            Logger.getLogger(Device.class.getName()).log(Level.FINE, "Connecting input: {0}, output: {1}", new Object[]{selectedInput, selectedOutput});
            if ((selectedInput < 0) || (selectedOutput < 0)) {
                return false;
            }
            Command c = new SetCrosspointCommand(selectedInput + 1, selectedOutput + 1);
            if (deviceWriteRead(c, PairSequencePayload.class/*, 1500*/)) {
                PairSequencePayload p = (PairSequencePayload) c.getPayload();
                selectedInput = p.get(selectedOutput + 1) - 1;
            }
        }
        cxnMatrix.put(selectedOutput, selectedInput);
        return true;
    }

    //------------------------------------------------------------------------
    public Enumeration<Preset> getPresets() {
        return getPresets(false);
    }
    public Enumeration<Preset> getPresets(final boolean live) {
        return getPresets(live, false);
    }
    public Enumeration<Preset> getPresets(final boolean live, final boolean full) {
        return new Enumeration() {

            int index = 0;

            @Override
            public boolean hasMoreElements() {
                if (connected && !isPushing() && (resetPresets || live)) {
                    boolean r = index < numPresets;
                    if (!r) {
                        resetPresets = false;
                    }
                    return r;
                }
                return index < presets.size();
            }

            @Override
            public Preset nextElement() {
                if (connected && !isPushing() && (resetPresets || live || full)) {
                    Command c = new GetPresetNameCommand(index + 1);
                    if (deviceWriteRead(c, IdNamePayload.class)) {
                        IdNamePayload p = (IdNamePayload) c.getPayload();
                        String name = p.getStrData();
                        Preset newP = new Preset(name, index+1);

                        // if we're doing a full pull down, we need to get all the
                        // connections for the preset.
                        if (full)
                        {
                            Command c2 = new GetPresetCommand(index + 1);
                            if (deviceWriteRead(c2, PresetReportPayload.class)) {
                                PresetReportPayload p2 = (PresetReportPayload) c2.getPayload();
                                for(int i=0; i<numInputs; i++)
                                {
                                    newP.makeConnection(p2.getInputForOutput(i+1)-1, i);
                                }
                            }
                        } else {
                            Preset oldP = presets.get(index);
                            newP.setConnections(oldP.getConnections());
                        }
                        presets.set(index, newP);
                    }
                }
                if (DELAY > 0) {
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException ex) {
                        //Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                setProgress((float) index / presets.size());
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
            public boolean isReset() {
                return resetInput;
            }

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
                return numInputs;
            }

            @Override
            protected Connector makeNewConnector(String name, int icon, int index) {
                return new Input(name, icon, index);
            }

            @Override
            public Command getIconLookupCommand(int index) {
                return new GetInputIconCommand(index);
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
                return numOutputs;
            }

            @Override
            protected Connector makeNewConnector(String name, int icon, int index) {
                return new Output(name, icon, index);
            }

            @Override
            public Command getIconLookupCommand(int index) {
                return new GetOutputIconCommand(index);
            }
        };
    }

    //------------------------------------------------------------------------
    public HashMap<Integer, Integer> getCrossPoints() {
        return getCrossPoints(true);
    }

    public HashMap<Integer, Integer> getCrossPoints(boolean live) {
        if (connected && (resetXP || live)) {
            Command c = new GetAllCrosspointsCommand();
            if (deviceWriteRead(c, SequencePayload.class)) {
                SequencePayload p = (SequencePayload) c.getPayload();
                for (int i = 0; i < p.size(); i++) {
                    cxnMatrix.put(i/*Output*/, p.get(i) - 1/*Input*/);
                }
                resetXP = false;
            }
        }
        return cxnMatrix;
    }

    //------------------------------------------------------------------------
    public void setFullReset(boolean reset) {
        resetInput = resetOutput = resetPresets = resetXP = reset;
    }
    public void setPresetReset(boolean reset) {
        resetPresets = reset;
    }
    //------------------------------------------------------------------------

    public void setSelectedOutput(int selectedOutput) {
        this.selectedOutput = selectedOutput;
    }

    //------------------------------------------------------------------------
    public Connector getSelectedOutput() {
        try {
            return (Connector) outputs.get(selectedOutput);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    //------------------------------------------------------------------------
    public void setSelectedInput(int selectedInput) {
        this.selectedInput = selectedInput;
    }

    //------------------------------------------------------------------------
    public Connector getSelectedInput() {
        try {
            return (Connector) inputs.get(selectedInput);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }

    }

    //------------------------------------------------------------------------
    public void loadPreset(int number) {
        // get the preset from the array
        Preset preset = presets.get(number);

        if (connected) {
            Command cmd = new TriggerPresetCommand(preset.getIndex());
            deviceWriteAndSkip(cmd);

            /*
            cmd = new GetAllCrosspointsCommand();
            if (deviceWriteRead(cmd, SequencePayload.class/*,2000 * /)) {
                SequencePayload p = (SequencePayload) cmd.getPayload();
                for (int i = 0; i < p.size(); i++) {
                    cxnMatrix.put(i/*Output * /, p.get(i) - 1/*Input * /);
                }
                resetXP = false;
            }*/

            //if (deviceWriteRead(cmd, PresetReportPayload.class))
            //    preset = readPresetReport(preset.getName(),
            //                              preset.getIndex(),
            //                              (PresetReportPayload) cmd.getPayload());
        }

        // set the connection matrix from this
        cxnMatrix = preset.getConnections();
    }

    //------------------------------------------------------------------------
    public void push(Preset p)
    {
        if (isConnected() && isPushing())
        {
            SetPresetCommand saveCmd = new SetPresetCommand(p.getIndex());
            for (int i=0; i<p.getConnections().size(); i++)
            {
                SequencePayload pld = (SequencePayload) saveCmd.getPayload();
                HashMap<Integer,Integer> cxns = p.getConnections();
                pld.add(cxns.get(i));
            }
            deviceWriteAndSkip(saveCmd);
            SetPresetNameCommand nameCmd = new SetPresetNameCommand(p.getIndex(), p.getName());
            deviceWriteAndSkip(nameCmd);
        }
    }
    
    public void savePreset(int number, String name) {
        // Get current preset
        Preset newPreset = new Preset(name, number+1);

        // Setup the set preset command while filling up new preset
        SetPresetCommand saveCmd = new SetPresetCommand(number+1);
        for (int i = 0; i < cxnMatrix.size(); i++) {
            // add the input to the payload in the correct slot
            SequencePayload p = (SequencePayload) saveCmd.getPayload();
            p.add(cxnMatrix.get(i) + 1);

            // add the input and output to the new internal Preset
            newPreset.makeConnection(cxnMatrix.get(i), i);
        }

        if (connected) {
            setProgress(1f / 3);
            if (deviceWriteRead(saveCmd, PresetReportPayload.class/*, 2000*/)) {
                newPreset = readPresetReport(name, number+1, (PresetReportPayload) saveCmd.getPayload());
            }
            setProgress(2f / 3);
            SetPresetNameCommand nameCmd = new SetPresetNameCommand(number+1, name);
            if (deviceWriteRead(nameCmd, IdNamePayload.class))
                newPreset.setName( ((IdNamePayload)nameCmd.getPayload()).getStrData() );
            setProgress(3f / 3);
        }

        presets.set(number, newPreset);
        resetPresets = true;
    }

    //------------------------------------------------------------------------
    private Preset readPresetReport(String name, int number, PresetReportPayload pld) {
        Preset p = new Preset(name, number);
        for (int i = 1; i <= numOutputs; i++) {
            p.makeConnection(pld.getInputForOutput(i), i);
        }
        return p;
    }

    //------------------------------------------------------------------------
    public int getNumOutputs() {
        return numOutputs;
    }

    public void setNumOutputs(int no) {
        numOutputs = no;
        resetOutput = true;
    }

    public int getNumInputs() {
        return numInputs;
    }

    public void setNumInputs(int ni) {
        numInputs = ni;
        resetInput = true;
    }

    public int getNumPresets() {
        return numPresets;
    }

    public void setNumPresets(int np) {
        numPresets = np;
        resetPresets = true;
    }

    public int getPassLength() {
        return maxPassLength;
    }
    public void setPassLength(int length) {
        maxPassLength = length;
    }
    
    public int getIONameLength() {
        return maxIONameLength;
    }
    
    public int getPresetNameLength() {
        return maxPresetNameLength;
    }

    public void setPresetNameLength(int pnl) {
        maxPresetNameLength = pnl;
    }

    public void setConnection(Connection cxn) throws IOException {
        if (isConnected()) {
            disconnect();
        }
        connection = (IPConnection) cxn;
        getConfiguration().setProperty("ipAddr", ((IPConnection)cxn).getIpAddr());
        getConfiguration().setProperty("port", ""+((IPConnection)cxn).getPort());
        saveConfiguration();
    }

    public Connection getConnection() {
        return connection;
    }

    /* For property change listener support */
    private void setProgress(float progress) {
        pcsupport.firePropertyChange("progress", this.progress, progress);
        this.progress = progress;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcsupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcsupport.removePropertyChangeListener(listener);
    }

    public void addConnectionListener(ConnectionListener c) {
        cxnEvtSupport.addConnectionListener(c);
    }

    public void removeConnectionListener(ConnectionListener c) {
        cxnEvtSupport.removeConnectionListener(c);
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
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, "Tried to get a message digest of the password.", ex);
        }

        return digested;
    }

    //------------------------------------------------------------------------
    // Password operations
    // Network library just truncates password if it's too long.
    private String setPassword(Command cmdIn, String pwd) {
        String newPwd = pwd;
        if (connected) {
            if (deviceWriteRead(cmdIn, SequencePayload.class)) {
                SequencePayload p = (SequencePayload) cmdIn.getPayload();
                int status = p.get(0);
                if (status != 0) {
                    newPwd = this.adminPassword;
                }
            }
        }
        return newPwd;
    }

    public void setAdminPassword(String pwd) {
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
            Command cmd = new ToggleLockCommand(password);
            if (deviceWriteRead(cmd, SequencePayload.class)) {
                SequencePayload p = (SequencePayload) cmd.getPayload();
                int status = p.get(0);
                locked = status == 0;
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
            } catch (NoSuchAlgorithmException ex) {
                // bah!
            }

            // check equality
            if (passHash != null && submittedDigest != null
                    && java.util.Arrays.equals(passHash, submittedDigest)) {
                locked = false;
            }
        }

        return !locked;
    }

    public void save() {
        if (connected)
        {
            Command c = new ForceSaveCommand();
            deviceWriteAndSkip(c);
        }
    }

    public void lock() {
        // send code to machine to lock itself.
        if (connected) {
            Command c = new ToggleLockCommand();
            if (deviceWriteRead(c, SequencePayload.class)) {

                SequencePayload p = (SequencePayload) c.getPayload();
                int status = p.get(0);
                locked = status == 0;
            }
        } else {
            locked = true;
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public void push() {
        if (!isConnected()) // throw exception
        {
            return;
        }

        return;
    }

    public void push(Connector c)
    {
        if (isConnected() && isPushing())
        {
            if (c instanceof Input)
            {
                pushInputName((Input)c);
                pushInputIcon((Input)c);
            }
            else
            {
                pushOutputName((Output)c);
                pushOutputIcon((Output)c);
            }
        }
    }

    private void pushInputName(Input i) {
        if (isConnected())
        {
            Command c = new SetConnectorNameCommand(i.getName(), i.getIndex(), true /* not input */);
            deviceWriteAndSkip(c);
            resetInput = true;
        }
    }

    private void pushOutputName(Output o) {
        if (isConnected())
        {
            Command c = new SetConnectorNameCommand(o.getName(), o.getIndex(), false /* not input */);
            deviceWriteAndSkip(c);
            resetOutput = true;
        }
    }

    private void pushInputIcon(Input i) {
        if (isConnected())
        {
            Command c = new SetConnectorIconCommand(i.getIndex(), i.getIcon(), true /* input */);
            deviceWriteAndSkip(c);
            resetInput = true;
        }
    }

    private void pushOutputIcon(Output o) {
        if (isConnected())
        {
            Command c = new SetConnectorIconCommand(o.getIndex(), o.getIcon(), false /* NOT input */);
            deviceWriteAndSkip(c); // just assume that it goes through.
            resetOutput = true;
        }
    }

    private void deviceWriteAndSkip(Command cmdOut)
    {
        try {
            connection.write(cmdOut);
        }   catch (IOException ex) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, "Exception while writing to the device!", ex);
        }
        try {
            Thread.sleep(300);
            connection.clearInput(500);
        } catch (InterruptedException ex) {
            //Logger.getLogger(Device.class.getName()).log(Level.SEVERE, "Exception while sleeping between a write and read to the device.", ex);
        } catch (IOException ex) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, "Exception while reading from the device!", ex);
        }
    }

    /// WARNING!!! MUTATES cmdOut!!!
    private boolean deviceWriteRead(Command cmdOut, Class payloadClass) {
/*        return deviceWriteRead(cmdOut, payloadClass, 0);
    }

    private boolean deviceWriteRead(Command cmdOut, Class payloadClass, int sleepTime) {
*/
        boolean obtained = false;
        Command cmdIn = null;
        int i = 0;
        boolean skipWrite = false;
        while (!obtained && i < MAX_TRIES) {
            try {
                // 'clear' the input stream before a write
                //connection.getInStream().skip(connection.getInStream().available());
                if (! skipWrite)
                {
                    connection.write(cmdOut);
                    //if (sleepTime > 0) {
                    //    Thread.sleep(sleepTime);
                    //}
                }
                try {
                    cmdIn = connection.readOne();
                    obtained = payloadClass.isInstance(cmdIn.getPayload());
                } catch (UnidentifiedMessageException ex)
                {
                    skipWrite = true;
                }
            } catch (IOException ex) {
                if (ex instanceof SocketTimeoutException)
                {
                    Logger.getLogger(Device.class.getName()).log(Level.FINE, "Gotcha", ex);
                }
                Logger.getLogger(Device.class.getName()).log(Level.INFO, "Unexpected situation when doing I/O", ex);
                i++;
            } /*catch (InterruptedException ex) {
                Logger.getLogger(Device.class.getName()).log(Level.WARNING, null, ex);
            }*/
        }
        if (obtained) {
            cmdOut.setPayload(cmdIn.getPayload());
        }
        return obtained;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object src = evt.getSource();
        if (src instanceof Connector) {
            if ("name".equals(evt.getPropertyName())) {
                if (src instanceof Input) {
                    Input i = (Input) src;
                    Logger.getLogger(getClass().getCanonicalName()).log(Level.FINE, "Detected name change on input #{0}", i.getIndex());
                    pushInputName(i);
                } else {
                    Output o = (Output) src;
                    Logger.getLogger(getClass().getCanonicalName()).log(Level.FINE, "Detected name change on output #{0}", o.getIndex());
                    pushOutputName(o);
                }
            }
            if ("icon".equals(evt.getPropertyName())) {
                if (src instanceof Input) {
                    Input i = (Input) src;
                    Logger.getLogger(getClass().getCanonicalName()).log(Level.FINE, "Detected icon change on input #{0}", i.getIndex());
                    pushInputIcon(i);
                } else {
                    Output o = (Output) src;
                    Logger.getLogger(getClass().getCanonicalName()).log(Level.FINE, "Detected icon change on output #{0}", o.getIndex());
                    pushOutputIcon(o);
                }
            }
        }
    }

    public void lockAdmin()
    {
        if (isConnected())
        {
            Command cmd = new ToggleUtilityLockCommand();
            if (deviceWriteRead(cmd, SequencePayload.class))
            {
                if (((SequencePayload)cmd.getPayload()).get(0) > 0)
                {
                    adminUnlocked = false;
                } else {
                    activeAdminPassword = "";
                    adminUnlocked = true;
                }
            }
        }
    }

    public void unlockAdmin(String password)
    {
        if (isConnected())
        {
            Command cmd = new ToggleUtilityLockCommand(password);
            if (deviceWriteRead(cmd, SequencePayload.class))
            {
                if (((SequencePayload)cmd.getPayload()).get(0) > 0)
                {
                    activeAdminPassword = password;
                    adminUnlocked = true;
                } else {
                    activeAdminPassword = "";
                    adminUnlocked = false;
                }
            }
        }
    }

    public boolean isAdminLocked() {
        if (isConnected())
        {
            Command cmd = new GetAdminLockStatusCommand();
            if (deviceWriteRead(cmd,SequencePayload.class/*,500*/))
            {
                if (((SequencePayload)cmd.getPayload()).get(0) > 0)
                {
                    adminUnlocked = true;
                } else {
                    adminUnlocked = false;
                }
            }
        }
        return ! adminUnlocked;
    }

    public void setPulling(boolean b) {
        pulling = b;
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
            this(l, false);
        }

        public ConnectorEnumeration(List<Connector> l, boolean live) {
            list = l;
            this.live = live;
        }

        @Override
        public boolean hasMoreElements() {
            if (connected && !isPushing() && (live || isReset())) {
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
            if (connected && !isPushing() && (live || isReset())) {
                Command c = getNameLookupCommand(index + 1);
                String name = null;
                int icon = 0;
                if (deviceWriteRead(c, IdNamePayload.class)) {
                    IdNamePayload p = (IdNamePayload) c.getPayload();
                    name = p.getStrData();
                }
                c = getIconLookupCommand(index + 1);
                if (deviceWriteRead(c, SequencePayload.class)) {
                    SequencePayload p = (SequencePayload) c.getPayload();
                    icon = p.get(1);
                }

                Connector ctr = makeNewConnector(name, icon, index + 1);
                ctr.addPropertyChangeListener(Device.this);
                list.set(index, ctr);
            }
            if (DELAY > 0) {
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException ex) {
                    //Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            setProgress((float) index / list.size());
            return (Connector) list.get(index++);
        }

        public abstract boolean isReset();

        public abstract void setReset(boolean r);

        public abstract Command getNameLookupCommand(int paramInt);
        public abstract Command getIconLookupCommand(int i);

        public abstract int getMax();

        protected abstract Connector makeNewConnector(String name, int icon, int index);

    }
}