package com.intelix.digihdmi.util;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class DigiRadioButton extends JRadioButton {

    private static ResourceMap rMap;
    private static ImageIcon checkedImg = null;
    private static ImageIcon basicImg = null;

    private static ResourceMap rMap()
    {
        if (rMap == null)
            rMap = Application.getInstance().getContext().getResourceMap(
                    DigiRadioButton.class);
        return rMap;
    }

    private static ImageIcon checkedImage()
    {
        if (checkedImg == null)
        {
            checkedImg = rMap().getImageIcon("radioSelected");
        }
        return checkedImg;
    }

    private static ImageIcon uncheckedImage()
    {
        if (basicImg == null)
        {
            basicImg = rMap().getImageIcon("radio");
        }
        return basicImg;
    }

    public DigiRadioButton() {
        super();
        this.setIcon(uncheckedImage());
        this.setSelectedIcon(checkedImage());
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

}
