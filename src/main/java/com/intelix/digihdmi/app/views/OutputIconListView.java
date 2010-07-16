package com.intelix.digihdmi.app.views;

/**
 *
 * @author mcaron
 */
public class OutputIconListView extends ButtonListView {

    @Override
    protected ButtonContainerPanel createButtonsPanel() {
        return new OutputIconContainerPanel();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
