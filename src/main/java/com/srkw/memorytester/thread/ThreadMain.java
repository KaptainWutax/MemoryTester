package com.srkw.memorytester.thread;

import com.srkw.memorytester.gui.GuiMain;
import com.srkw.memorytester.handler.ConfigHandler;

public class ThreadMain extends Thread {
	
	GuiMain guiInstance;

	public ThreadMain() {
				
	}
	
	@Override
	public void run() {
		
		guiInstance = new GuiMain();
		guiInstance.frame.setVisible(true);
		
		while(true) {
			updateText();
			try {Thread.currentThread().sleep(500);} catch (InterruptedException e) {}			
		}
		
	}
	
	public void updateText() {
		
		long maxMemory = Runtime.getRuntime().maxMemory() / 1000000;
		long freeMemory = Runtime.getRuntime().freeMemory() / 1000000;
		long maxMemoryRecommended = ConfigHandler.recommendedMemoryAllocated;
		
		guiInstance.memoryAllocatedText.setText(
			(maxMemory - freeMemory) + "MB of memory in use over " + (maxMemory) + "MB."
		);			
		
		if(maxMemoryRecommended > maxMemory) {
			guiInstance.memoryAllocatedRecommendedText.setText(
				"The pack recommends " + maxMemoryRecommended + "MB to run, please consider using " + (maxMemoryRecommended - maxMemory) + " more."
			);
		} else {
			guiInstance.memoryAllocatedRecommendedText.setText(
					"The pack recommends " + maxMemoryRecommended + "MB to run."
			);
		}
		
	}
	
}
