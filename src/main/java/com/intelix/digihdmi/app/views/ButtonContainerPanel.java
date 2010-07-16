package com.intelix.digihdmi.app.views;

import java.awt.LayoutManager;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class ButtonContainerPanel extends JPanel {

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
        b.setText(name);
        b.setName(name.replace(' ', '_') + "_" + this.buttonList.size());
        addButton(b);
    }

    public AbstractButton createButton(String name, String iconName) {
        return new JButton(name);
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
