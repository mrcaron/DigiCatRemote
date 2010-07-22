package com.intelix.digihdmi.app.views;

public class ConnectorSelectionView extends ButtonListView {

    @Override
    protected void initializeHome2Panel() {
        super.initializeHome2Panel();
    }

    @Override
    protected ButtonContainerPanel createButtonsPanel() {
        return new ToggleButtonContainerPanel();
    }
}
