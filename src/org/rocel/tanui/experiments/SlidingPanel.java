package org.rocel.tanui.experiments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

/**
 * A sliding panel.
 * 
 * @author Yassuo Toda
 */
public class SlidingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final SlidingPanel slidingPanel = new SlidingPanel(JScrollBar.HORIZONTAL);

                    JPanel pnlLeft = new JPanel();
                    pnlLeft.setBorder(null);
                    pnlLeft.setOpaque(false);
                    slidingPanel.addPanel(pnlLeft);

                    JButton btnLeft = new JButton("to right");
                    btnLeft.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            slidingPanel.slideToRight();
                        }
                    });
                    pnlLeft.add(btnLeft);

                    JPanel pnlRight = new JPanel();
                    pnlRight.setBorder(null);
                    pnlRight.setOpaque(false);
                    slidingPanel.addPanel(pnlRight);

                    JButton btnRight = new JButton("to left");
                    btnRight.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            slidingPanel.slideToLeft();
                        }
                    });
                    pnlRight.add(btnRight);

                    JPanel contentPane = new JPanel(new BorderLayout(0, 0));
                    contentPane.setBackground(Color.RED);
                    contentPane.add(slidingPanel, BorderLayout.CENTER);

                    JFrame frame = new JFrame();
                    frame.setContentPane(contentPane);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static final long TIME = 300;
    private static final long INTERVAL = 20;
    private static final int FRAMES = (int) (TIME / INTERVAL);

    private SldPnlViewport viewport;
    private JPanel view;
    private SldPnlBar bar;
    private boolean circular = false;

    private SlideRunnable slideRunnable;
    private BouncyRunnable bouncyRunnable;

    /**
     * Create the panel.
     */
    public SlidingPanel(int orientation) {

        setBorder(null);
        setOpaque(false);

        setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        if (orientation == JScrollBar.VERTICAL) {
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        } else {
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        }
        add(scrollPane);

        viewport = new SldPnlViewport();
        viewport.setBorder(null);
        viewport.setOpaque(false);
        scrollPane.setViewport(viewport);

        view = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        view.setBorder(null);
        view.setOpaque(false);
        scrollPane.setViewportView(view);

        bar = new SldPnlBar(orientation, viewport);
        bar.setBorder(null);
        bar.setOpaque(false);
        bar.setUI(null);
        if (orientation == JScrollBar.VERTICAL) {
            scrollPane.setVerticalScrollBar(bar);
        } else {
            scrollPane.setHorizontalScrollBar(bar);
        }

        JPanel leftBorder = new JPanel();
        leftBorder.setOpaque(false);
        leftBorder.setBorder(null);
        viewport.setMax(leftBorder, Short.MAX_VALUE);
        view.add(leftBorder);

        JPanel rightBorder = new JPanel();
        rightBorder.setOpaque(false);
        rightBorder.setBorder(null);
        viewport.setMax(rightBorder, Short.MAX_VALUE);
        view.add(rightBorder);

        slideRunnable = new SlideRunnable(); 
        bouncyRunnable = new BouncyRunnable(); 
    }

    public void addPanel(Component component) {
        addPanel(component, Short.MAX_VALUE);
    }

    public void addPanel(Component component, int max) {
        viewport.setMax(component, max);
        view.add(component, view.getComponentCount() - 1);
    }

    public Component getPanel(int i) {
        return view.getComponent(i + 1);
    }

    public int getPanelCount() {
        return view.getComponentCount() - 2;
    }

    public float getPos() {
        return bar.getPos();
    }

    public void setPos(float f) {
        bar.setPos(f);
    }

    public void swap() {
        float pos = getPos();
        if (pos >= 1) {
            Component component = view.getComponent(1);
            view.setComponentZOrder(component, getPanelCount());
            view.revalidate();
            setPos(pos - 1);
        } else if (pos <= 0) {
            Component component = view.getComponent(getPanelCount());
            view.setComponentZOrder(component, 1);
            view.revalidate();
            setPos(pos + 1);
        }
    }

    public void setCircular(boolean circular) {
        this.circular = circular;
    }

    public boolean isCircular() {
        return circular;
    }

    public void slideToBottom() {
        slideToRight();
    }

    public void slideToTop() {
        slideToLeft();
    }

    public void slideToRight() {
        float pos = getPos();
        float f = (float) (Math.floor(pos) + 1);
        if (f > (getPanelCount() - 1)) {
            if (circular) {
                swap();
                f = f - 1;
            } else {
                bouncy(f - .5f);
                return;
            }
        }
        slide(f);
    }

    public void slideToLeft() {
        float pos = getPos();
        float f = (float) (Math.ceil(pos) - 1);
        if (f < 0) {
            if (circular) {
                swap();
                f = f + 1;
            } else {
                bouncy(f + .5f);
                return;
            }
        }
        slide(f);
    }

    public void slide(float f) {
        Runtime.getRuntime().gc();
        slideRunnable.set(f);
        Thread t = new Thread(slideRunnable); 
        t.start();
    }

    public void bouncyToBottom() {
        bouncyToRight();
    }

    public void bouncyToTop() {
        bouncyToLeft();
    }

    public void bouncyToRight() {
        float pos = getPos();
        float f = (float) (Math.floor(pos) + 1);
        bouncy(f - .5f);
    }

    public void bouncyToLeft() {
        float pos = getPos();
        float f = (float) (Math.ceil(pos) - 1);
        bouncy(f + .5f);
    }

    public void bouncy(float f) {
        Runtime.getRuntime().gc();
        bouncyRunnable.set(f);
        Thread t = new Thread(bouncyRunnable); 
        t.start();
    }

    private SlidingPanel parent;

    public void setParent(SlidingPanel parent) {
        this.parent = parent;
    }

    public boolean isShowing() {
        boolean showing = super.isShowing();
        if (!showing) {
            return false;
        }
        if (parent == null) {
            return showing;
        }
        if (parent.isVisible(this)) {
            return true;
        }
        return false;
    }

    public boolean isVisible(Component component) {
        if (getPos() != ((int) getPos())) {
            return false;
        }
        Component c = view.getComponent((int) getPos() + 1);
        return (component == c);
    }

    private class SlideRunnable implements Runnable {

        private float f;

        public void set(float f) {
            this.f = f;
        }

        public void run() {
            synchronized (view) {
                if (getPos() == f) {
                    return;
                }
                float s1 = f;
                float s0 = (float) (getPos() < f ? Math.floor(getPos()) : Math.ceil(getPos()));
                int i0 = (int) (Math.acos(1 - 2 * (getPos() - s0) / (s1 - s0)) * FRAMES / Math.PI) + 1;
                if (((s1 - s0) > 1) || ((s0 - s1) > 1)) {
                    i0 = FRAMES / 2;
                }
                for (int i = i0; i < FRAMES; i++) {
                    float s = (float) ((1 - Math.cos(i * Math.PI / FRAMES)) / 2 * (s1 - s0) + s0);
                    setPos(s);
                    try {
                        Thread.sleep(INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                setPos(s1);
                if (runnable != null) {
                    runnable.run();
                    runnable = null;
                }
            }
        }
    }

    private class BouncyRunnable implements Runnable {

        private float f;

        public void set(float f) {
            this.f = f;
        }

        public void run() {
            synchronized (view) {
                float s1 = f;
                float s0 = getPos();
                for (int i = 0; i < (FRAMES / 2); i++) {
                    float s = (float) ((1 - Math.cos(i * Math.PI / (FRAMES / 2))) / 2 * (s1 - s0) + s0);
                    setPos(s);
                    try {
                        Thread.sleep(INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = ((FRAMES / 2) - 1); i >= 0 ; i--) {
                    float s = (float) ((1 - Math.cos(i * Math.PI / (FRAMES / 2))) / 2 * (s1 - s0) + s0);
                    setPos(s);
                    try {
                        Thread.sleep(INTERVAL);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                setPos(s0);
                if (runnable != null) {
                    runnable.run();
                    runnable = null;
                }
            }
        }
    }

    private Runnable runnable;

    public void cascade(Runnable runnable) {
        this.runnable = runnable;
    }

    private class SldPnlBar extends JScrollBar {

        private static final long serialVersionUID = 1L;

        private Dimension ZERO = new Dimension(0, 0);

        private float pos;
        private Container view;

        public SldPnlBar(int orientation, JViewport viewport) {
            super(orientation);
            view = (Container) viewport.getView();
        }

        public float getPos() {
            return pos;
        }

        public Dimension getPreferredSize() {
            if (getUI() == null) {
                return ZERO;
            } else {
                return super.getPreferredSize();
            }
        }

        public void setPos(float pos) {
            this.pos = pos;
            int value = getValue(pos);
            setValue(value);
        }

        public void setValues(int newValue, int newExtent, int newMin, int newMax) {
            super.setValues(newValue, newExtent, newMin, newMax);
            setPos(pos);
        }

        private int getValue(float f) {
            int minimum = 0;
            int floor = (int) Math.floor(f);
            Component component = null;
            for (int i = -1; i < floor; i++) {
                component = view.getComponent(i + 1);
                minimum += (getOrientation() == JScrollBar.HORIZONTAL ? component.getWidth() : component.getHeight());
            }
            int maximum = 0;
            int ceil = (int) Math.ceil(f);
            for (int i = -1; i < ceil; i++) {
                component = view.getComponent(i + 1);
                maximum += (getOrientation() == JScrollBar.HORIZONTAL ? component.getWidth() : component.getHeight());
            }
            int value = (int) ((maximum - minimum) * (f - floor) + minimum);
            return value;
        }
    }

    private class SldPnlViewport extends JViewport {

        private static final long serialVersionUID = 1L;

        private HashMap<Component, Integer> maxMap;

        public SldPnlViewport() {
            maxMap = new HashMap<Component, Integer>();
        }

        public void setBounds(int x, int y, int width, int height) {
        Container view = (Container) getView();
        int count = view.getComponentCount();
            JScrollPane scrollPane = (JScrollPane) getParent();
            if (scrollPane.getVerticalScrollBarPolicy() == ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS) {
                int viewHeight = 0;
                for (int i = 0; i < count; i++) {
                    Component component = view.getComponent(i); 
                    int componentHeight = maxMap.get(component).intValue();
                    if (componentHeight == GroupLayout.DEFAULT_SIZE) {
                        componentHeight = Math.max(height, component.getMinimumSize().height);
                    } else if (componentHeight == GroupLayout.PREFERRED_SIZE) {
                        componentHeight = Math.max(component.getPreferredSize().height, component.getMinimumSize().height);
                    } else if (componentHeight == Short.MAX_VALUE) {
                        componentHeight = height;
                    }
                    if (componentHeight < 0) {
                        componentHeight = 0;
                    }
                    component.setPreferredSize(new Dimension(width, componentHeight));
                    viewHeight += componentHeight;
                }
                view.setPreferredSize(new Dimension(width, viewHeight));
            } else if (scrollPane.getHorizontalScrollBarPolicy() == ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS) {
                int viewWidth = 0;
                for (int i = 0; i < count; i++) {
                    Component component = view.getComponent(i); 
                    int componentWidth = maxMap.get(component).intValue();
                    if (componentWidth == GroupLayout.DEFAULT_SIZE) {
                        componentWidth = Math.max(width, component.getMinimumSize().width);
                    } else if (componentWidth == GroupLayout.PREFERRED_SIZE) {
                        componentWidth = Math.max(component.getPreferredSize().width, component.getMinimumSize().width);
                    } else if (componentWidth == Short.MAX_VALUE) {
                        componentWidth = width;
                    }
                    if (componentWidth < 0) {
                        componentWidth = 0;
                    }
                    component.setPreferredSize(new Dimension(componentWidth, height));
                    viewWidth += componentWidth;
                }
                view.setPreferredSize(new Dimension(viewWidth, height));
            } else {
                throw new RuntimeException("Scroll bar policy not allowed.");
            }
            super.setBounds(x, y, width, height);
        }

        public void setMax(Component component, int max) {
            maxMap.put(component, new Integer(max));
        }
    }
}