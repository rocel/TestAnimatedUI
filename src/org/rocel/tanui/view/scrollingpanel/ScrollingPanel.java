package org.rocel.tanui.view.scrollingpanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class ScrollingPanel extends JPanel {
	private GridBagLayout gb;
	private GridBagConstraints constraints;
	
	private static final long serialVersionUID = 1725989152545424225L;

	public ScrollingPanel(){
		setLayout(new GridBagLayout());
		this.constraints = new GridBagConstraints();
		this.constraints.gridx = 0;
		this.constraints.gridy = GridBagConstraints.RELATIVE;
		this.constraints.fill = GridBagConstraints.HORIZONTAL; 
	}

	@Override
	public Component add(Component comp) {
		comp.setPreferredSize(new Dimension(comp.getWidth(), comp.getHeight()));
		comp.setMinimumSize(new Dimension(comp.getWidth(), comp.getHeight()));
		super.add(comp,this.constraints);
		return comp;
	}

	
}
