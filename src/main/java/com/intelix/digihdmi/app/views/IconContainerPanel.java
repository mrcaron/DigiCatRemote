package com.intelix.digihdmi.app.views;

import com.intelix.digihdmi.util.ImageButton;
import java.awt.Dimension;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author mcaron
 */
public class IconContainerPanel extends ButtonContainerPanel {

    Dimension iconSize = new Dimension(72, 72);

    @Override
    protected AbstractButton createButton(String name, String iconName) {
        ImageButton b = new ImageButton(iconName, iconSize);
        b.setStretch(false);
        
        return b;
    }

    @Override
    protected int getNoColumns() {
        return 4;
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
                
                final IconContainerPanel lv = new IconContainerPanel();

                lv.setOpaque(false);
                JScrollPane scrollPane = new JScrollPane(lv);
                scrollPane.setOpaque(false);
                scrollPane.getViewport().setOpaque(false);

                f.getContentPane().add(scrollPane);
                
                f.setSize(400,400);

                SwingWorker w = new SwingWorker() {

                    @Override
                    protected Object doInBackground() throws Exception {
                        for (int i=0; i<10; i++)
                            lv.addButton("Button_1", "ps_apple_tv", null);

                        return null;
                    }
                };
                f.setVisible(true);
                w.execute();

            }
        });
    }
}
