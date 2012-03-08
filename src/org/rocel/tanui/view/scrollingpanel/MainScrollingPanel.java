package org.rocel.tanui.view.scrollingpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainScrollingPanel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setBounds(100, 100, 500, 500);
					frame.setTitle("ScrollingPanel");
					ScrollingPanel contentPanel = new ScrollingPanel();
					fillPanel(contentPanel);
					frame.getContentPane().add(contentPanel,BorderLayout.CENTER);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected static void fillPanel(ScrollingPanel contentPanel) {
		int nbPanel = 20;
		for (int i=0 ; i<nbPanel; i++){
			JPanel p = new JPanel();
			p.setSize(500, 30);
			p.setBackground(new Color(
					(int)(Math.random() * (255)),
					(int)(Math.random() * (255)),
					(int)(Math.random() * (255)))
			);
			p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			contentPanel.add(p);
		}
	}

}
