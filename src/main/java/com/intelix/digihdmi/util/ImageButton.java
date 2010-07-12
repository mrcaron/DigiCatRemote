/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;

/**
 *
 * @author mcaron
 */
public class ImageButton extends JButton {

    boolean stretch = true;
    Dimension minSize = new Dimension(72,72);

    public ImageButton() {
        this("");
    }

    public ImageButton(String imageName)
    {
        this(imageName,null);

    }
    public ImageButton(String imageName, Dimension minSize)
    {
        this.imageName = imageName;
        
        if (minSize != null)
            this.minSize = minSize;

        init();
    }

    public void setStretch(boolean stretch) {
        this.stretch = stretch;
    }

    private void init() {
        URL rUrl;
        //InputStream fontStream;
        try {
            rUrl = getClass().getResource("resources/" + imageName + ".png");
            //fontStream = getClass().getResourceAsStream(fontName);
            if (rUrl != null) {
                BufferedImage img = ImageIO.read(rUrl);
                bgImage = img;
                aspectRatio = bgImage.getWidth(this) / bgImage.getHeight(this);
                /* Font processing
                Font f = java.awt.Font.createFont(Font.TRUETYPE_FONT, fontStream);
                buttonFont = f.deriveFont(Font.PLAIN, 72);
                 */
                Dimension d = minSize;
                setSize(d);
                setMinimumSize(d);
                setPreferredSize(d);
                setOpaque(false);
                setBorderPainted(false);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(TextImageButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TextImageButton.class.getName()).log(Level.SEVERE, null, ex);
        } /*catch (FontFormatException ex) {
        Logger.getLogger(ImageButton.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        /*catch (FontFormatException ex) {
        Logger.getLogger(ImageButton.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Image i = bgImage.getScaledInstance(
                stretch ? getWidth() : minSize.width,
                stretch ? getHeight(): minSize.height,
                Image.SCALE_SMOOTH);
        g.drawImage(i, 0, 0, null);
        /*
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(buttonFont);
        g2d.drawString(buttonText, 50, 50);
         */
    }


    float aspectRatio;
    //Font buttonFont;
    Image bgImage;
    String buttonText;
    String imageName;
}
