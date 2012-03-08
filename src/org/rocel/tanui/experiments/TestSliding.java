package org.rocel.tanui.experiments;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.rocel.tanui.view.slidingPanels.JSLSlider;

public class TestSliding {
	
    public static void main(String[] args) {
        JFrame f=new JFrame("Test slider component app");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(initSlider());
        f.pack();
        f.setState(JFrame.MAXIMIZED_BOTH);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
 
    public static JComponent initSlider() {
        JSLSlider slider = new JSLSlider();
        slider.addSliderComponent(new JButton("JButton instance - component 1"));
        slider.addSliderComponent(new JLabel("Long text JLabel instance - component 2"));
        slider.addSliderComponent(new JTextField("JTextField instance - component 3"));
        slider.addSliderComponent(new JTextField("JTextField instance - component 4"));
        slider.refresh();
        return slider;
    }

}