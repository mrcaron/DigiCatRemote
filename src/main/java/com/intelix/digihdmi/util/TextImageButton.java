/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author developer
 */
public class TextImageButton extends ImageButton {
    //Font buttonFont;

    public TextImageButton() {
        this("RoomViewBtn");
    }

    public TextImageButton(String imageName)
    {
        super(imageName, new Dimension(294, 66));
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Swing paint demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(250, 250);
        //f.setUndecorated(true);
        f.setLocationRelativeTo(null);
        JPanel p = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.black);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        };
        p.setLayout(new MigLayout("", "[grow]", "[grow]"));

        JButton b = new TextImageButton();
        p.add(b, "align center");

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello!");
            }
        });

        p.add(b, "align center");
        f.setContentPane(p);
        f.pack();
        p.revalidate();
        f.setVisible(true);
    }

}
