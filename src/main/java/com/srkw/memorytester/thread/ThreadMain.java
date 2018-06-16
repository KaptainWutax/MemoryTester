package com.srkw.memorytester.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.srkw.memorytester.data.DataGeneral;
import com.srkw.memorytester.gui.GuiErrorCrash;
import com.srkw.memorytester.gui.GuiForceCrash;
import com.srkw.memorytester.gui.GuiMain;
import com.srkw.memorytester.gui.GuiMenu;

<<<<<<< Updated upstream
public class ThreadMain extends Thread {
=======
import java.io.*;

	public ThreadMain() {	
	}
	
	@Override
	public void run() {
		
		Thread.currentThread().setName("MemoryTesterThread");
		
		parseData();
		
		if(Runtime.getRuntime().maxMemory() / 1000000 < maxMemoryRecommended && forceCrash) {
			guiForceCrashInstance = new GuiForceCrash((ThreadMain) Thread.currentThread());
			guiForceCrashInstance.frame.setVisible(true);
			guiForceCrashInstance.crash.setText(
					"The game was forced crashed because of insufficient memory."
			);
			guiForceCrashInstance.currentAllocation.setText(					
					"Your current memory allocation is " + Runtime.getRuntime().maxMemory() / 1000000 + "MB."
			);
			guiForceCrashInstance.recommendedAllocation.setText(					
					"Please allocate " + maxMemoryRecommended + "MB before running."
			);
			guiForceCrashInstance.crashInfo.setText(
					"If you would like to play with a lower allocation, contact the pack maker to adjust the settings."
			);
			while(true) {try {Thread.currentThread().sleep(100);} catch (InterruptedException e) {}}
		}
		
		guiMenuInstance = new GuiMenu((ThreadMain) Thread.currentThread());
		guiMenuInstance.frame.setVisible(true);
		
		while(true) {
			
			if(isInMenu) {
				holdPause();
			} else {
				if(guiMainInstance == null) {
					guiMainInstance = new GuiMain((ThreadMain) Thread.currentThread());
					guiMainInstance.frame.setVisible(true);
				}
				updateText();
			}
			
			try {Thread.currentThread().sleep(memoryTextUpdateDelay);} catch (InterruptedException e) {}			
		}
		
	}
	
	public void parseData() {
>>>>>>> Stashed changes

	//GUI
	public GuiMenu guiMenuInstance;
	public GuiMain guiMainInstance;
	public GuiForceCrash guiForceCrashInstance;
	public GuiErrorCrash guiErrorCrashInstance;
    
    //DATA OBJECTS
    public DataGeneral generalData;
      
    //DATA VALUES
    public int recommendedMemoryAllocation;
    public long memoryTextUpdateDelay;
    public boolean forceCrash;
    public String crashInfo;
    public String redirectCrashLink;
    
    //LISTENER FLAG
    public boolean hasGameInitialized = false;
    public boolean isInMenu = true;
    public boolean shouldGameStart = true;
 
    //HELPERS
    private String threadName;
    private long maxMemory;
    private long totalMemory;
    private long freeMemory;

    public ThreadMain() {}

    @Override
    public void run() {
        setThreadName("MemoryTesterThread");
        initialize();
    }
    
    public void initialize() {
        updateMemoryStatistics();
        
        generateDataInstances();      
        generalData.configLoadData();
        
        if (recommendedMemoryAllocation > maxMemory && forceCrash) {inGuiForceCrashAction();}

        
        while (true) { 
            if (isInMenu) {inGuiMenuAction();} else {
                while(!hasGameInitialized) {performSleep(100000000);}
            	inGuiMainAction();
            	}
            performSleep(memoryTextUpdateDelay);
        }
    }
    
    private void setThreadName(String name) {
    	Thread.currentThread().setName(name);
    	this.threadName = name;
    }
      
    private void updateMemoryStatistics() {
        maxMemory = Runtime.getRuntime().maxMemory() / 1000000;
        totalMemory = Runtime.getRuntime().totalMemory() / 1000000;
        freeMemory = Runtime.getRuntime().freeMemory() / 1000000;
    }
    
    private void generateDataInstances() {
    	if(generalData == null) {
    		generalData = new DataGeneral((ThreadMain) Thread.currentThread());
    	}
    }

    private void inGuiMenuAction() {
    	if (guiMenuInstance == null) {
            guiMenuInstance = new GuiMenu((ThreadMain) Thread.currentThread());
            guiMenuInstance.frame.setVisible(true);
    	}
    	updateGuiMenu();
    }
    
    private void inGuiMainAction() {
        if (guiMainInstance == null) {
            guiMainInstance = new GuiMain((ThreadMain) Thread.currentThread());
            guiMainInstance.frame.setVisible(true);
        }
        updateGuiMain();
    }

    public void inGuiForceCrashAction() {   	
    	if(guiForceCrashInstance == null) {
    		guiForceCrashInstance = new GuiForceCrash((ThreadMain) Thread.currentThread());
    		guiForceCrashInstance.frame.setVisible(true);
    	}
    	updateGuiForceCrash();
        while (true) {performSleep(memoryTextUpdateDelay);}
    }
    
    
    public void inGuiErrorCrash(String errorLocation) {
    	if(guiErrorCrashInstance == null) {
    		guiErrorCrashInstance = new GuiErrorCrash((ThreadMain) Thread.currentThread());
    		guiErrorCrashInstance.frame.setVisible(true);
    	}
    	updateGuiErrorCrash(errorLocation);
    	while (true) {performSleep(memoryTextUpdateDelay);}
    }
 
    private void performSleep(long millis) {
        try {Thread.currentThread().sleep(millis);} 
        catch (InterruptedException e) {;}
    }

    private void updateGuiMenu() {
    	
        updateMemoryStatistics();
        
        guiMenuInstance.memoryAllocatedText.setText(
                "You have " + maxMemory + "MB of memory allocated."
        );

        if (recommendedMemoryAllocation > maxMemory) {
            guiMenuInstance.memoryAllocatedRecommendedText.setText(
                    "The pack recommends " + recommendedMemoryAllocation + "MB to run, please consider using " + (recommendedMemoryAllocation - maxMemory) + " more."
            );
        } else {
            guiMenuInstance.memoryAllocatedRecommendedText.setText(
                    "The pack recommends " + recommendedMemoryAllocation + "MB to run."
            );
        }

    }

    private void updateGuiMain() {
    	
        updateMemoryStatistics();

        guiMainInstance.memoryAllocatedText.setText(
                (totalMemory - freeMemory) + "MB of memory in use over " + (maxMemory) + "MB."
        );

        if (recommendedMemoryAllocation > maxMemory) {
            guiMainInstance.memoryAllocatedRecommendedText.setText(
                    "The pack recommends " + recommendedMemoryAllocation + "MB to run, please consider using " + (recommendedMemoryAllocation - maxMemory) + " more."
            );
        } else {
            guiMainInstance.memoryAllocatedRecommendedText.setText(
                    "The pack recommends " + recommendedMemoryAllocation + "MB to run."
            );
        }

        guiMainInstance.usage.add(totalMemory - freeMemory);

        long memoryTraceSum = 0;
        for (long memoryTrace : guiMainInstance.usage) memoryTraceSum += memoryTrace;
        long memoryTraceAverage = (memoryTraceSum - guiMainInstance.usage.get(0) + guiMainInstance.usage.get(0) * guiMainInstance.weight) / (guiMainInstance.usage.size() + guiMainInstance.weight - 1);

        double memoryTracePercentage = (memoryTraceAverage * 100) / maxMemory;

        guiMainInstance.memoryAllocatedRatioUsageText.setText(
                "Consumption of " + memoryTraceAverage + "MB in average, representing " + memoryTracePercentage + "% of the total memory."
        );

        if (guiMainInstance.usage.size() > 120) {
            guiMainInstance.weight += guiMainInstance.usage.size();
            guiMainInstance.usage.clear();
            guiMainInstance.usage.add(memoryTraceAverage);
        }

        if (maxMemory - freeMemory > maxMemory - maxMemory * 0.05) {
            guiMainInstance.memorySpikesCount++;
        }

        if (guiMainInstance.memorySpikesCount == 0) {
            guiMainInstance.memorySpikesText.setText(
                    "No memory spikes were recorded. Minecraft is running smoothly."
            );
        } else if (guiMainInstance.memorySpikesCount == 1) {
            guiMainInstance.memorySpikesText.setText(
                    "1 memory spike was recorded."
            );
        } else {
            guiMainInstance.memorySpikesText.setText(
                    guiMainInstance.memorySpikesCount + " memory spikes were recorded."
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
     
    private void updateGuiForceCrash() {  
    	
        guiForceCrashInstance.crash.setText(
                "The game was forced crashed because of an insufficient memory allocation."
        );
        guiForceCrashInstance.currentAllocation.setText(
                "Your current memory allocation is " + maxMemory + "MB."
        );
        guiForceCrashInstance.recommendedAllocation.setText(
                "Please allocate " + recommendedMemoryAllocation + "MB before running."
        );
        guiForceCrashInstance.crashInfo.setText(
                crashInfo
        );
        
    }
    
    private void updateGuiErrorCrash(String errorLocation) {
    	
    	guiErrorCrashInstance.crash.setText(
    			"The game was forced crashed because of illegal configuration."
    	);
    	
    	guiErrorCrashInstance.crashInfo.setText(
    			errorLocation
    	);
    	
    }

}
