package org.rocel.tanui.view.slidingPanels;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;

public class SliderCardLayout extends CardLayout{
	private static final long serialVersionUID = -5937710289971014500L;

	public Component getCurrentComponent(Container parent) {
        int n = parent.getComponentCount();
        for (int i = 0 ; i < n ; i++) {
            Component comp = parent.getComponent(i);
            if (comp.isVisible()) {
                return comp;
            }
        }
        return null;
    }
 
    public Component getNextComponent(Container parent) {
        int n = parent.getComponentCount();
        for (int i = 0 ; i < n ; i++) {
            Component comp = parent.getComponent(i);
            if (comp.isVisible()) {
                int currentCard = (i + 1) % n;
                comp = parent.getComponent(currentCard);
                return comp;
            }
        }

        return null;
    }
    
    public Component getPreviousComponent(Container parent) {
        int n = parent.getComponentCount();
        for (int i = 0 ; i < n ; i++) {
            Component comp = parent.getComponent(i);
            if (comp.isVisible()) {
                int currentCard = ((i > 0) ? i-1 : n-1);
                comp = parent.getComponent(currentCard);
                return comp;
            }

        }
        return null;
    }
}