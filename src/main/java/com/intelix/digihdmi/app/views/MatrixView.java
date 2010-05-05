package com.intelix.digihdmi.app.views;

import javax.swing.JComponent;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class MatrixView extends ApplicationView {

    MatrixPanel panel;

    @Override
    protected JComponent createRightComponent() {
        // could be optimized by NOT creating a new view EACH time we get
        // the right panel of the view. This is not a great way to go.
        panel = new MatrixPanel();
        return panel;
    }

    public MatrixPanel getMatrixPanel() {
        return panel;
    }

}
