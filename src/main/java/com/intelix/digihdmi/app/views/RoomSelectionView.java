package com.intelix.digihdmi.app.views;

public class RoomSelectionView extends ButtonListView {

    protected void initializeBottomPanel() {
        super.initializeBottomPanel();
    }

    protected ButtonContainerPanel createButtonsPanel() {
        return new InputButtonContainerPanel();
    }
}
