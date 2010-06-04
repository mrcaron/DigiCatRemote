/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author mcaron
 */
public class PaintedJPanel extends JPanel {
    private Image bgImage;

    public PaintedJPanel() {
        URL rUrl;
        //InputStream fontStream;
        try {
            rUrl = getClass().getResource("/com/intelix/digihdmi/util/resources/home screen-large.png");
            if (rUrl != null) {
                BufferedImage img = ImageIO.read(rUrl);
                bgImage = img;
                //setOpaque(false);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(PaintedJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PaintedJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // put background on
        /*
        Image i;
        if (getWidth() > getHeight())
        {
            i = bgImage.getScaledInstance(getWidth(), -1, Image.SCALE_SMOOTH);
        } else {
            i = bgImage.getScaledInstance(-1, getHeight(), Image.SCALE_SMOOTH);
        }*/
        g.drawImage(bgImage, 0, 0, this);
    }
}
