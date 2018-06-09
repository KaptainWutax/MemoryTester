package com.srkw.memorytester.thread;

import com.srkw.memorytester.gui.GuiMain;

public class ThreadMain extends Thread {

	public ThreadMain() {
				
	}
	
	@Override
	public void run() {
		GuiMain instance = new GuiMain();
		instance.frame.setVisible(true);
		while(true) {
			
			instance.memoryAllocatedText.setText(
					(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1000000 +
				"MB of memory in use over " + Runtime.getRuntime().maxMemory() / 1000000 +
				"MB."
			);
			
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
