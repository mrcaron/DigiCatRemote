package com.intelix.digihdmi.app.views;

import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class ButtonContainerPanel extends JPanel {

    private static HashMap<String, Icon> cache = new HashMap<String, Icon>();
    private final String GET_ICONS_HERE = "/com/intelix/digihdmi/util/resources";

    ArrayList<AbstractButton> buttonList = new ArrayList();

    public ButtonContainerPanel() {
        initComponents();
        setOpaque(false);
    }

    private void initComponents() {
        setName("Form");
        setLayout(createLayout());
    }

    protected LayoutManager createLayout()
    {
        return new MigLayout(
                System.getProperty("DEBUG") != null ?
                    "debug," : "" +
                    (useGrid() ? "wrap " + getNoColumns() : "nogrid"),
                    (useGrid() ? getRowLayoutString() : ""));
    }

    protected boolean useGrid() { return true; }
    protected int getNoColumns() { return 2; }
    protected String getColumnLayoutString() { return "[grow,fill]"; }
    protected int getColPadding() { return 15; }
    private String getRowLayoutString() {
        StringBuilder b = new StringBuilder();
        for (int i=0; i<getNoColumns(); i++)
        {
            b.append(getColumnLayoutString());
            b.append(getColPadding());
        }
        return b.toString();
    }

    public final void addButton(AbstractButton b)
    {
        this.buttonList.add(b);
        b.setOpaque(false);
        add(b);
        validate();
        if (getParent() != null)
            getParent().validate();
    }

    public final void addButton(String name, Action action) {
        addButton(name, null, action);
    }

    public final void addButton(String name, String iconName, Action action) {
        AbstractButton b = createButton(name, iconName);
        b.setAction(action);
        if (iconName != null)
            b.setIcon(cache.get(iconName));
        b.setText(name);
        b.setName(name.replace(' ', '_') + "_" + this.buttonList.size());
        addButton(b);
    }

    public AbstractButton createButton(String name, String iconName) {
        JButton b;
        
        if (iconName != null && !iconName.isEmpty())
        {
            String key = iconName;
            Icon i = cache.get(key);
            if (null == i)
            {
                i = loadIconImage(iconName);
                cache.put(key,i);
            }
            b = new JButton(name, i);
        } else
        {
            b = new JButton(name);
        }

        return b;
    }

    public Icon loadIconImage(String iconName)
    {
        URL imageURL = getClass().getResource(GET_ICONS_HERE + "/" + iconName + ".png");
        BufferedImage img = null;
        Image resizedImg = null;
        ImageIcon icn = null;
        try {
            img = ImageIO.read(imageURL);
            resizedImg = img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            Logger.getLogger(ButtonContainerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (img != null && resizedImg != null)
        {
            icn = new ImageIcon(resizedImg);
        }
        return icn;
    }

    public AbstractButton getButton(int index) {
        return (AbstractButton) this.buttonList.get(index);
    }

    public void clear() {
        removeAll();
        this.buttonList.clear();
        revalidate();
    }
}
