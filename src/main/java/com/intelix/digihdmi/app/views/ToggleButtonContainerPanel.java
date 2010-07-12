package com.intelix.digihdmi.app.views;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractButton;
import javax.swing.JToggleButton;

public class ToggleButtonContainerPanel extends ButtonContainerPanel
        implements ItemListener {

    protected AbstractButton selectedButton;

    public ToggleButtonContainerPanel() {
        this.selectedButton = null;
    }

    @Override
    protected AbstractButton createButton(String name, String iconName) {
        JToggleButton b = new JToggleButton(name);
        b.addItemListener(this);
        return b;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        AbstractButton b = (AbstractButton) e.getSource();
        int state = e.getStateChange();
        if (state == 1) {
            if (this.selectedButton != null) {
                this.selectedButton.setSelected(false);
            }
            this.selectedButton = ((JToggleButton) b);
        }
    }
}
