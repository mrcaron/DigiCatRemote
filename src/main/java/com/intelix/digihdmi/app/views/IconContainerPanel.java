package com.intelix.digihdmi.app.views;

import com.intelix.digihdmi.util.ImageButton;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author mcaron
 */
public abstract class IconContainerPanel extends ButtonContainerPanel {

    Dimension iconSize = new Dimension(72, 72);
    // cache for image buttons so they aren't created new all the time
    HashMap<String, ImageButton> iconButtons = new HashMap<String, ImageButton>();
    int numIcons;

    public IconContainerPanel() {
        super();
        ResourceBundle props = ResourceBundle.getBundle(getClass().getPackage().getName() + ".resources." + getClass().getSimpleName());
        numIcons = Integer.parseInt(props.getString("numInputIcons"));
    }

    @Override
    public AbstractButton createButton(String name, String iconName) {

        ImageButton b = iconButtons.get(iconName);
        if (null == b)
        {
            b = new ImageButton(iconName, iconSize);
            iconButtons.put(iconName, b);
            b.setStretch(false);
        }

        return b;
    }

    @Override
    protected int getNoColumns() {
        return 4;
    }

    public int getNumIcons()
    {
        return numIcons;
    }

    @Override
    protected String getColumnLayoutString() {
        return "[grow,center]";
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Icon List Panel");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                final IconContainerPanel lv = new InputIconContainerPanel();

                lv.setOpaque(false);
                JScrollPane scrollPane = new JScrollPane(lv);
                scrollPane.setOpaque(false);
                scrollPane.getViewport().setOpaque(false);

                f.getContentPane().add(scrollPane);
                
                f.setSize(400,400);

                f.setVisible(true);

                SwingWorker<Void,AbstractButton> w = new SwingWorker() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMinimumIntegerDigits(2);

                        for (int i=1; i<=lv.getNumIcons(); i++)
                        {
                            publish(lv.createButton("Button_" + i, "input_" + nf.format(i)));
                        }
                        return null;
                    }

                    @Override
                    protected void process(List chunks) {
                        for (ImageButton b : (List<ImageButton>)chunks)
                            lv.addButton(b);
                    }
                };
                w.execute();
            }
        });
    }
}
