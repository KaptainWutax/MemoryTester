package com.srkw.memorytester.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.srkw.memorytester.gui.GuiMain;
import com.srkw.memorytester.gui.GuiMenu;

public class ThreadMain extends Thread {	 
	
	GuiMenu guiMenuInstance;
	GuiMain guiMainInstance;
	
	public int maxMemoryRecommended;
	public long memoryTextUpdateDelay;
	public boolean isInMenu = true;
	public boolean shouldGameStart = true;

	public ThreadMain() {	
	}
	
	@Override
	public void run() {
		
		Thread.currentThread().setName("MemoryTesterThread");
		
		parseData();
		
		guiMenuInstance = new GuiMenu((ThreadMain) Thread.currentThread());
		guiMenuInstance.frame.setVisible(true);
		
		while(true) {
			
			if(isInMenu) {
				holdPause();
			} else {
				if(guiMainInstance == null) {
					guiMainInstance = new GuiMain(Thread.currentThread());
					guiMainInstance.frame.setVisible(true);
				}
				updateText();
			}
			
			try {Thread.currentThread().sleep(memoryTextUpdateDelay);} catch (InterruptedException e) {}			
		}
		
	}
	
	public void parseData() {

		FileReader input = null;
		try {
			input = new FileReader("config/memorytester.txt");
		} catch (FileNotFoundException e2) {
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter("config/memorytester.txt"));
				writer.write("RecommendedMemoryAllocation=1024\nMemoryTextUpdateDelay=500");
				writer.flush();
				writer.close();
				input = new FileReader("config/memorytester.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		try {		
			BufferedReader bufRead = new BufferedReader(input);
			
			String myLine = bufRead.readLine();  
			String[] data = myLine.split("=");
			
			for(int i = 0 ; i < data.length ; i++) {
				if(data[i].trim().contains("RecommendedMemoryAllocation")) {
					maxMemoryRecommended = Integer.parseInt(data[i + 1].trim());
				}
			}		
			
			myLine = bufRead.readLine();  
			data = myLine.split("=");
			
			for(int i = 0 ; i < data.length ; i++) {
				if(data[i].trim().contains("MemoryTextUpdateDelay")) {
					memoryTextUpdateDelay = Integer.parseInt(data[i + 1].trim());
				}
			}
			
			bufRead.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void holdPause() {
		
		long maxMemory = Runtime.getRuntime().maxMemory() / 1000000;
		long freeMemory = Runtime.getRuntime().freeMemory() / 1000000;
		
		guiMenuInstance.memoryAllocatedText.setText(
				"You have " + maxMemory + "MB of memory allocated."
		);
		
		if(maxMemoryRecommended > maxMemory) {
			guiMenuInstance.memoryAllocatedRecommendedText.setText(
					"The pack recommends " + maxMemoryRecommended + "MB to run, please consider using " + (maxMemoryRecommended - maxMemory) + " more."
			);
		} else {
			guiMenuInstance.memoryAllocatedRecommendedText.setText(
					"The pack recommends " + maxMemoryRecommended + "MB to run."
			);
		}
		
	}
	
	public void updateText() {
		
		long maxMemory = Runtime.getRuntime().maxMemory() / 1000000;
		long freeMemory = Runtime.getRuntime().freeMemory() / 1000000;
		
		guiMainInstance.memoryAllocatedText.setText(
			(maxMemory - freeMemory) + "MB of memory in use over " + (maxMemory) + "MB."
		);			
		
		if(maxMemoryRecommended > maxMemory) {
			guiMainInstance.memoryAllocatedRecommendedText.setText(
				"The pack recommends " + maxMemoryRecommended + "MB to run, please consider using " + (maxMemoryRecommended - maxMemory) + " more."
			);
		} else {
			guiMainInstance.memoryAllocatedRecommendedText.setText(
					"The pack recommends " + maxMemoryRecommended + "MB to run."
			);
		}
						
		guiMainInstance.usage.add(maxMemory - freeMemory);
		
		long memoryTraceSum = 0;
		for (long memoryTrace : guiMainInstance.usage) memoryTraceSum += memoryTrace;
		long memoryTraceAverage = (memoryTraceSum - guiMainInstance.usage.get(0) + guiMainInstance.usage.get(0) * guiMainInstance.weight) / (guiMainInstance.usage.size() + guiMainInstance.weight - 1);
		
		double memoryTracePercentage = (memoryTraceAverage * 100) / maxMemory;
		
		guiMainInstance.memoryAllocatedRatioUsageText.setText(
				"Consumption of " + memoryTraceAverage + "MB in average, representing " + memoryTracePercentage + "% of the total memory."
		);
			
		if(guiMainInstance.usage.size() > 120) {
			guiMainInstance.weight += guiMainInstance.usage.size();
			guiMainInstance.usage.clear();
			guiMainInstance.usage.add(memoryTraceAverage);
		}
		
		if(maxMemory - freeMemory > maxMemory - maxMemory * 0.05) {
			guiMainInstance.memorySpikesCount++;
		}
		
		if(guiMainInstance.memorySpikesCount == 0) {
			guiMainInstance.memorySpikesText.setText(
					"No memory spikes were recorded. Minecraft is running smoothly."
			);
		} else if(guiMainInstance.memorySpikesCount == 1) {
			guiMainInstance.memorySpikesText.setText(
					"1 memory spike was recorded. This is not good."
			);
		} else {
			guiMainInstance.memorySpikesText.setText(
					guiMainInstance.memorySpikesCount + " memory spikes were recorded. This is not good."
			);
		}
		
		long hour = guiMainInstance.hoursPassed;
		long minute = guiMainInstance.minutesPassed;
		long second = guiMainInstance.secondsPassed;
		long milliseconds = guiMainInstance.millisecondsPassed;
		
		guiMainInstance.totalTimeText.setText(
				"Game has been running for " + hour + " hours, " + minute + " minutes, " + second + " seconds and " + milliseconds + " milliseconds."
		);
		
	}
	
}
