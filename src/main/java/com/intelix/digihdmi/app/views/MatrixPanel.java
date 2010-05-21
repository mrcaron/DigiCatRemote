/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.app.views;

import com.intelix.digihdmi.util.BasicAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author developer
 */
public class MatrixPanel extends JPanel {

    // WARNING! THESE ARE HARD CODED VALUES AND DO NOT REFLECT
    // A DIGI-HDMI THAT IS ANYTHING MORE OR LESS. WILL BREAK
    // IF NOT USED WITH AN 8X8!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    int rows = 8;  // THIS IS FOR THE DEMO ONLY!
    int cols = 8;  // THIS IS FOR THE DEMO ONLY!

    JLabel lbl;
    ArrayList<JRadioButton> radioButtons = new ArrayList<JRadioButton>();
    ArrayList<ButtonGroup> outputSelections = new ArrayList<ButtonGroup>();
    ArrayList<JLabel> outputLabels = new ArrayList<JLabel>();
    ArrayList<JLabel> inputLabels = new ArrayList<JLabel>();

    public MatrixPanel() {
        this(8,8,null);
    }

    public MatrixPanel(Action defaultAction) {
        this(8,8,defaultAction);
    }

    public MatrixPanel(int rows, int cols, Action defaultAction) {
        this.rows = rows;
        this.cols = cols;
        initComponents();
        setDefaultButtonAction(defaultAction);
    }

    protected void initComponents() {
        setLayout(new MigLayout("align 50% 50%, filly, gapx 10, wrap " + (cols+1) , "", ""));

        for (int x=0; x<cols; x++)
        {
            JLabel l = new JLabel("OUT_" + x);
            outputLabels.add(l);
            this.add(l, x==0 ? "skip" : "");
        }

        for (int y=0; y<rows; y++) /* INPUTS */
        {
            JLabel lin = new JLabel("IN_" + y);
            inputLabels.add(lin);
            this.add(lin);
            for (int x=0; x<cols; x++)  /* OUTPUTS */
            {
                ButtonGroup g;
                try {
                    g = outputSelections.get(x);
                } catch (IndexOutOfBoundsException e)
                {
                    g = new ButtonGroup();
                    outputSelections.add(g);
                }

                JRadioButton b = new JRadioButton();
                b.setName("b_" + x + "_" + y);   // name is "b_<OUTPUT>_<INPUT>"
                b.setSelected(y == 0);

                g.add(b);
                radioButtons.add(b);
                this.add(b,"align 50% 50%");
            }
        }
    }

    public void reset() {
        initComponents();
    }

    public void setRows(int rows) {
        if (this.rows != rows)
        {
            this.rows = rows;
        }
    }

    // stick with only adjusting the columns, then everything will get reset
    public void setCols(int cols) {
        if (this.cols != cols)
        {
            this.cols = cols;
            initComponents();
        }
    }

    public void setInputName(int i, String name) {
        inputLabels.get(i).setText(name);
    }

    public void setOutputName(int i, String name) {
        outputLabels.get(i).setText(name);
    }

    public void setDefaultButtonAction(Action a)
    {
        Iterator<JRadioButton> allButtons = radioButtons.iterator();
        while(allButtons.hasNext())
        {
            allButtons.next().setAction(a);
        }
    }

    public void select(Integer output, Integer input, boolean init) {
        JRadioButton b = radioButtons.get(cols*input+output);
        if (init)
            b.setSelected(true);
        else
            b.doClick();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Vertical Label");
                MatrixPanel lv = new MatrixPanel();
                f.getContentPane().add(lv);
                f.setSize(600,400);
                f.setVisible(true);
            }
        });
    }
}
