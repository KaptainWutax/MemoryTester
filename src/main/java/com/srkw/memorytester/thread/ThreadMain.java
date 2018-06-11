package com.srkw.memorytester.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.srkw.memorytester.gui.GuiErrorCrash;
import com.srkw.memorytester.gui.GuiForceCrash;
import com.srkw.memorytester.gui.GuiMain;
import com.srkw.memorytester.gui.GuiMenu;

public class ThreadMain extends Thread {

	//GUI
    private GuiMenu guiMenuInstance;
    private GuiMain guiMainInstance;
    private GuiForceCrash guiForceCrashInstance;
    private GuiErrorCrash guiErrorCrashInstance;
    
    //CONFIG
    private int recommendedMemoryAllocation = 1024;
    private long memoryTextUpdateDelay = 500;
    private boolean forceCrash = false;
    private String crashInfo = "If you would like to play with a lower allocation, contact the pack maker to adjust the settings.";
    
    //LISTENER FLAG
    public boolean isInMenu = true;
    public boolean shouldGameStart = true;
 
    //HELPERS
    String threadName;
    long maxMemory;
    long totalMemory;
    long freeMemory;

    public ThreadMain() {}

    @Override
    public void run() {
        setThreadName("MemoryTesterThread");
        updateMemoryStatistics();
        configLoadData();
        
        if (recommendedMemoryAllocation > maxMemory && forceCrash) {inGuiForceCrashAction();}
        
        while (true) {  
            if (isInMenu) {inGuiMenuAction();} else {inGuiMainAction();}
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

    private void inGuiForceCrashAction() {   	
    	if(guiForceCrashInstance == null) {
    		guiForceCrashInstance = new GuiForceCrash((ThreadMain) Thread.currentThread());
    		guiForceCrashInstance.frame.setVisible(true);
    	}
    	updateGuiForceCrash();
        while (true) {performSleep(memoryTextUpdateDelay);}
    }
    
    
    private void inGuiErrorCrash(String errorLocation) {
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
    
    private void configLoadData() {
    	
        FileReader input = null;
        BufferedWriter writer = null;
        
        try {input = new FileReader("config/MemoryTester/general.txt");} 
        catch (FileNotFoundException e) {     	
            try {
            	File dir = new File("config/MemoryTester");
            	dir.mkdir();
                writer = new BufferedWriter(new FileWriter("config/MemoryTester/general.txt"));
                writer.write(
                		"RecommendedMemoryAllocation=" + recommendedMemoryAllocation + "\n"
                        + "MemoryTextUpdateDelay=" + memoryTextUpdateDelay + "\n"
                        + "ForceCrash=" + forceCrash + "\n"
                        + "CrashInfo=" + crashInfo + "\n"
                );
                writer.flush();
                writer.close();
                input = new FileReader("config/MemoryTester/general.txt");
            } catch (IOException e2) {inGuiErrorCrash("Unexpected error in " + "config/MemoryTester/general.txt");}

        }
        
        try {configParseData(input);} catch (IOException e) {inGuiErrorCrash("ForceCrash entry in " + "config/MemoryTester/general.txt");}

    }
    
    private void configParseData(FileReader input) throws IOException {
    	
    	BufferedReader bufRead = new BufferedReader(input);

        String myLine = bufRead.readLine();
        String[] data = myLine.split("=");
        Boolean illegalArgument = false;

            if (data[0].trim().contains("RecommendedMemoryAllocation")) {
            	recommendedMemoryAllocation = Integer.parseInt(data[1].trim());
            } else {inGuiErrorCrash("RecommendedMemoryAllocation entry in " + "config/MemoryTester/general.txt");}

        myLine = bufRead.readLine();
        data = myLine.split("=");

            if (data[0].trim().contains("MemoryTextUpdateDelay")) {
                memoryTextUpdateDelay = Integer.parseInt(data[1].trim());
            } else {inGuiErrorCrash("MemoryTextUpdateDelay entry in " + "config/MemoryTester/general.txt");}

        myLine = bufRead.readLine();
        data = myLine.split("=");

            if (data[0].trim().contains("ForceCrash")) {
            	forceCrash = Boolean.parseBoolean(data[1].trim());
            } else {inGuiErrorCrash("ForceCrash entry in " + "config/MemoryTester/general.txt");}
        
        myLine = bufRead.readLine();
        data = myLine.split("=");

            if (data[0].trim().contains("CrashInfo")) {
                crashInfo = data[1].trim();
            } else {inGuiErrorCrash("CrashInfo entry in " + "config/MemoryTester/general.txt");}

        bufRead.close();
        
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
