package org.rocel.tanui.experiments;
 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Q03b {
 
    private JFrame mainFrame = null;
 
    public Q03b() {
        mainFrame = new JFrame("...");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        /* 1- Initialisation du container. */
        mainFrame.setLayout(new GridBagLayout());
 
        /*3- Ajout de ces composants en spécifiant les contraintes de type GridBagConstraints. */
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL; 
 
        int nbPanel = 20;
		for (int i=0 ; i<nbPanel; i++){
			JPanel p = new JPanel();
			p.setPreferredSize(new Dimension(300, 50));
			p.setMinimumSize(new Dimension(300, 50));
			p.setBackground(new Color(
					(int)(Math.random() * (255)),
					(int)(Math.random() * (255)),
					(int)(Math.random() * (255)))
			);
			p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			
			mainFrame.add(p,constraints);
		}
		
        mainFrame.setMinimumSize(new Dimension(400, 300));
        mainFrame.setLocationRelativeTo(null);
    }
 
     public void setVisible(boolean b) {
        mainFrame.setVisible(b);
    }
 
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Q03b q03b = new Q03b();
                q03b.setVisible(true);
            }
        });
    }
 
}