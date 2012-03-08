package org.rocel.tanui.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainVue extends JFrame {
	private static final long serialVersionUID = -2567077718174063578L;
	private JPanel contentPane;

	public MainVue(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setTitle("FabAPP");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		getContentPane().add(contentPane,BorderLayout.CENTER);
	}
	
	
	public void display() {
		this.setVisible(true);
	}	
	
}
