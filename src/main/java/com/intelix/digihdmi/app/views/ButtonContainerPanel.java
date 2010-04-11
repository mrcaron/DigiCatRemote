package com.intelix.digihdmi.app.views;

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
    }

    private void initComponents() {
        setName("Form");
        setLayout(new MigLayout("wrap 2", "[grow,fill]15[grow,fill]"));
    }

    public final void addButton(String name, Action action) {
        addButton(name, null, action);
    }

    public final void addButton(String name, String iconName, Action action) {
        AbstractButton b = createButton(name, iconName);
        this.buttonList.add(b);
        b.setAction(action);
        b.setText(name);
        b.setName(name.replace(' ', '_') + "_" + this.buttonList.size());

        add(b);
        validate();
        getParent().validate();
    }

    protected AbstractButton createButton(String name, String iconName) {
        return new JButton(name);
    }

    public AbstractButton getButton(int outputNumber) {
        return (AbstractButton) this.buttonList.get(outputNumber);
    }

    public void clear() {
        removeAll();
        this.buttonList.clear();
        revalidate();
    }
}
