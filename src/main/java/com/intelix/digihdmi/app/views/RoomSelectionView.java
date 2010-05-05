package com.intelix.digihdmi.app.views;

public class RoomSelectionView extends ButtonListView {

    @Override
    protected void initializeBottomPanel() {
        super.initializeBottomPanel();
    }

    @Override
    protected ButtonContainerPanel createButtonsPanel() {
        return new InputButtonContainerPanel();
    }
}
