package org.rocel.tanui;

import java.awt.EventQueue;

import org.rocel.tanui.view.MainVue;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainVue window = new MainVue();
					window.display();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
