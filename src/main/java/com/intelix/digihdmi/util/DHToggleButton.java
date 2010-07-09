package com.intelix.digihdmi.util;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JToggleButton;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class DHToggleButton extends JToggleButton implements Selectable {

    /**
     * Creates an initially unselected toggle button
     * without setting the text or image.
     */
    public DHToggleButton () {
        super(null, null, false);
    }

    /**
     * Creates an initially unselected toggle button
     * with the specified image but no text.
     *
     * @param icon  the image that the button should display
     */
    public DHToggleButton(Icon icon) {
        super(null, icon, false);
    }

    /**
     * Creates a toggle button with the specified image
     * and selection state, but no text.
     *
     * @param icon  the image that the button should display
     * @param selected  if true, the button is initially selected;
     *                  otherwise, the button is initially unselected
     */
    public DHToggleButton(Icon icon, boolean selected) {
        super(null, icon, selected);
    }

    /**
     * Creates an unselected toggle button with the specified text.
     *
     * @param text  the string displayed on the toggle button
     */
    public DHToggleButton (String text) {
        super(text, null, false);
    }

    /**
     * Creates a toggle button with the specified text
     * and selection state.
     *
     * @param text  the string displayed on the toggle button
     * @param selected  if true, the button is initially selected;
     *                  otherwise, the button is initially unselected
     */
    public DHToggleButton (String text, boolean selected) {
        super(text, null, selected);
    }

    /**
     * Creates a toggle button where properties are taken from the
     * Action supplied.
     *
     * @since 1.3
     */
    public DHToggleButton(Action a) {
        super(a);
    }

    /**
     * Creates a toggle button that has the specified text and image,
     * and that is initially unselected.
     *
     * @param text the string displayed on the button
     * @param icon  the image that the button should display
     */
    public DHToggleButton(String text, Icon icon) {
        super(text, icon, false);
    }

    /**
     * Creates a toggle button with the specified text, image, and
     * selection state.
     *
     * @param text the text of the toggle button
     * @param icon  the image that the button should display
     * @param selected  if true, the button is initially selected;
     *                  otherwise, the button is initially unselected
     */
    public DHToggleButton (String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }
}
