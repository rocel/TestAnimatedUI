package org.rocel.tanui.view.slidingPanels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;
 
public class JSLSlider extends JPanel {

    JPanel pnlMain = new JPanel(new SliderCardLayout());
    JPanel pnlPosition = new JPanel();
    
    JButton btnNext = new JButton(">");
    JButton btnPrevious = new JButton("<");
    
    public JSLSlider() {
        setLayout(new BorderLayout());
 
        pnlPosition.setBackground(new Color(200,12,100));
        
        add(pnlMain, BorderLayout.CENTER);
        add(btnNext, BorderLayout.EAST);
        add(btnPrevious, BorderLayout.WEST);
        add(pnlPosition, BorderLayout.SOUTH);
 
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });
        btnPrevious.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                previous();
            }
        });
 
    }
 
    public void previous() {
        SliderCardLayout cl=(SliderCardLayout)pnlMain.getLayout();
        Component currentComp=cl.getCurrentComponent(pnlMain);
        Component previousComp=cl.getPreviousComponent(pnlMain);
        Rectangle b=currentComp.getBounds();
        previousComp.setVisible(true);
        SliderListener sl=new SliderListener(10, currentComp, previousComp, false);
        Timer t=new Timer(40,sl);
        sl.timer=t;
        t.start();
    }

    public void next() {
        SliderCardLayout cl=(SliderCardLayout)pnlMain.getLayout();
        Component currentComp=cl.getCurrentComponent(pnlMain);
        Component nextComp=cl.getNextComponent(pnlMain);
        Rectangle b=currentComp.getBounds();
        nextComp.setVisible(true);
        SliderListener sl=new SliderListener(10, currentComp, nextComp, true);
        Timer t=new Timer(40,sl);
        sl.timer=t;
        t.start();
    }
 
    public void addSliderComponent(JComponent c) {
        pnlMain.add(c,""+getComponentCount());
    }
 
    public void refresh() {
        revalidate();
        repaint();
    }
 
    public class SliderListener implements ActionListener {
        Component c1;
        Component c2;
        int steps;
        int step=0;
        Timer timer;
        boolean isNext;
 
        public SliderListener(int steps, Component c1, Component c2, boolean isNext) {
            this.steps=steps;
            this.c1=c1;
            this.c2=c2;
            this.isNext=isNext;
        }
 
        public void actionPerformed(ActionEvent e) {
            Rectangle bounds=c1.getBounds();
            int shift=bounds.width/steps;
            if (!isNext) {
                c1.setLocation(bounds.x-shift, bounds.y);
                c2.setLocation(bounds.x-shift+bounds.width, bounds.y);
            }
            else {
                c1.setLocation(bounds.x+shift, bounds.y);
                c2.setLocation(bounds.x+shift-bounds.width, bounds.y);
            }
            pnlMain.repaint();
            step++;
            if (step==steps) {
                timer.stop();
                c2.setVisible(false);
                CardLayout cl=(CardLayout)pnlMain.getLayout();
                if (isNext) {
                    cl.next(pnlMain);
                }
                else {
                    cl.previous(pnlMain);
                }
            }
        }
    } 
}