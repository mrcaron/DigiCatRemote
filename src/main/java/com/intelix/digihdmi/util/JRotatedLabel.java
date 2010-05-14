/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class JRotatedLabel extends JLabel {
public enum Direction {
        HORIZONTAL,
        VERTICAL_UP,
        VERTICAL_DOWN,
        ANGLE_UP
    }
    private Direction direction;

    public JRotatedLabel() {
        super();
    }

    public JRotatedLabel(Direction direction, String content) {
        super(content);
        this.direction = direction;
    }

    @Override
    public Dimension getPreferredSize() {
        // swap size for vertical alignments
        switch (getDirection()) {
            case VERTICAL_UP:
            case VERTICAL_DOWN:
                return new Dimension(super.getPreferredSize().height, super.getPreferredSize().width);
            case ANGLE_UP:
                int max = Math.max(super.getPreferredSize().height, super.getPreferredSize().width);
                return new Dimension(max,max);
            default:
                return super.getPreferredSize();
        }
    }
    private boolean needsRotate;

    @Override
    public Dimension getSize() {
        if (!needsRotate) {
            return super.getSize();
        }

        Dimension size = super.getSize();

        switch (getDirection()) {
            case VERTICAL_DOWN:
            case VERTICAL_UP:
                return new Dimension(size.height, size.width);
            case ANGLE_UP:
                int max = Math.max(super.getSize().height, super.getSize().width);
                return new Dimension(max,max);
            default:
                return super.getSize();
        }
    }

    @Override
    public int getHeight() {
        return getSize().height;
    }

    @Override
    public int getWidth() {
        return getSize().width;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g.create();

        switch (getDirection()) {
            case VERTICAL_UP:
                gr.translate(0, getSize().getHeight());
                gr.transform(AffineTransform.getQuadrantRotateInstance(-1));
                break;
            case VERTICAL_DOWN:
                gr.transform(AffineTransform.getQuadrantRotateInstance(1));
                gr.translate(0, -getSize().getWidth());
                break;
            case ANGLE_UP:
                double s = getSize().getHeight();
                gr.translate(0, s - 50);
                gr.translate(s - 50, 0);
                //gr.transform(AffineTransform.getQuadrantRotateInstance(-1));
                gr.transform(AffineTransform.getRotateInstance(-0.785398163));
                //gr.drawLine(0, 0, 100, 100);
                break;
            default:
        }

        needsRotate = true;
        super.paintComponent(gr);
        needsRotate = false;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Vertical Label");
                JRotatedLabel lv = new JRotatedLabel(Direction.ANGLE_UP, "Hello World!");
                f.getContentPane().add(lv);
                f.pack();
                f.setVisible(true);
            }
        });
    }

}
