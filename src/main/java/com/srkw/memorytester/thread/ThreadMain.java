package com.srkw.memorytester.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.srkw.memorytester.gui.GuiForceCrash;
import com.srkw.memorytester.gui.GuiMain;
import com.srkw.memorytester.gui.GuiMenu;

public class ThreadMain extends Thread {

	//GUIS
    private GuiMenu guiMenuInstance;
    private GuiMain guiMainInstance;
    private GuiForceCrash guiForceCrashInstance;
    
    //CONFIG
    private int maxMemoryRecommended;
    private long memoryTextUpdateDelay;
    private boolean forceCrash = false;
    
    //LISTENER FLAGS
    public boolean isInMenu = true;
    public boolean shouldGameStart = true;

    
    long maxMemory = Runtime.getRuntime().maxMemory() / 1000000;
    long totalMemory = Runtime.getRuntime().totalMemory() / 1000000;
    long freeMemory = Runtime.getRuntime().freeMemory() / 1000000;

    public ThreadMain() {
    }

    @Override
    public void run() {

        Thread.currentThread().setName("MemoryTesterThread");

        parseData();
        
        maxMemory = Runtime.getRuntime().maxMemory() / 1000000;
        totalMemory = Runtime.getRuntime().totalMemory() / 1000000;
        freeMemory = Runtime.getRuntime().freeMemory() / 1000000;

        if (maxMemoryRecommended > maxMemory && forceCrash) {
            guiForceCrashInstance = new GuiForceCrash((ThreadMain) Thread.currentThread());
            guiForceCrashInstance.frame.setVisible(true);
            guiForceCrashInstance.crash.setText(
                    "The game was forced crashed because of an insufficient memory allocation."
            );
            guiForceCrashInstance.currentAllocation.setText(
                    "Your current memory allocation is " + maxMemory + "MB."
            );
            guiForceCrashInstance.recommendedAllocation.setText(
                    "Please allocate " + maxMemoryRecommended + "MB before running."
            );
            guiForceCrashInstance.crashInfo.setText(
                    "If you would like to play with a lower allocation, contact the pack maker to adjust the settings."
            );
            
            while (true) {
                try {Thread.currentThread().sleep(100);} catch (InterruptedException e) {}
            }
        }

        guiMenuInstance = new GuiMenu((ThreadMain) Thread.currentThread());
        guiMenuInstance.frame.setVisible(true);

        while (true) {

            if (isInMenu) {
                holdPause();
            } else {
                if (guiMainInstance == null) {
                    guiMainInstance = new GuiMain((ThreadMain) Thread.currentThread());
                    guiMainInstance.frame.setVisible(true);
                }
                updateText();
            }

            try {
                Thread.currentThread().sleep(memoryTextUpdateDelay);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

    }

    private void parseData() {

        FileReader input = null;
        BufferedWriter writer;
        try {input = new FileReader("config/memorytester.txt");} 
        catch (FileNotFoundException e) {
            try {
                writer = new BufferedWriter(new FileWriter("config/memorytester.txt"));
                writer.write(
                		"RecommendedMemoryAllocation=1024\n"
                        + "MemoryTextUpdateDelay=500\n"
                        + "ForceCrash=false\n"
                );
                writer.flush();
                writer.close();
                input = new FileReader("config/memorytester.txt");
            } catch (IOException e2) {}

        }

        try {
            BufferedReader bufRead = new BufferedReader(input);

            String myLine = bufRead.readLine();
            String[] data = myLine.split("=");

            for (int i = 0; i < data.length; i++) {
                if (data[i].trim().contains("RecommendedMemoryAllocation")) {
                    maxMemoryRecommended = Integer.parseInt(data[i + 1].trim());
                }
            }

            myLine = bufRead.readLine();
            data = myLine.split("=");

            for (int i = 0; i < data.length; i++) {
                if (data[i].trim().contains("MemoryTextUpdateDelay")) {
                    memoryTextUpdateDelay = Integer.parseInt(data[i + 1].trim());
                }
            }

            myLine = bufRead.readLine();
            data = myLine.split("=");

            for (int i = 0; i < data.length; i++) {
                if (data[i].trim().contains("ForceCrash")) {
                    if (data[i + 1].trim().contains("true")) {
                        forceCrash = true;
                    }
                    if (data[i + 1].trim().contains("false")) {
                        forceCrash = false;
                    }
                }
            }

            bufRead.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private void holdPause() {

        maxMemory = Runtime.getRuntime().maxMemory() / 1000000;
        totalMemory = Runtime.getRuntime().totalMemory() / 1000000;
        freeMemory = Runtime.getRuntime().freeMemory() / 1000000;

        guiMenuInstance.memoryAllocatedText.setText(
                "You have " + maxMemory + "MB of memory allocated."
        );

        if (maxMemoryRecommended > maxMemory) {
            guiMenuInstance.memoryAllocatedRecommendedText.setText(
                    "The pack recommends " + maxMemoryRecommended + "MB to run, please consider using " + (maxMemoryRecommended - maxMemory) + " more."
            );
        } else {
            guiMenuInstance.memoryAllocatedRecommendedText.setText(
                    "The pack recommends " + maxMemoryRecommended + "MB to run."
            );
        }

    }

    private void updateText() {

        maxMemory = Runtime.getRuntime().maxMemory() / 1000000;
        totalMemory = Runtime.getRuntime().totalMemory() / 1000000;
        freeMemory = Runtime.getRuntime().freeMemory() / 1000000;

        guiMainInstance.memoryAllocatedText.setText(
                (totalMemory - freeMemory) + "MB of memory in use over " + (maxMemory) + "MB."
        );

        if (maxMemoryRecommended > maxMemory) {
            guiMainInstance.memoryAllocatedRecommendedText.setText(
                    "The pack recommends " + maxMemoryRecommended + "MB to run, please consider using " + (maxMemoryRecommended - maxMemory) + " more."
            );
        } else {
            guiMainInstance.memoryAllocatedRecommendedText.setText(
                    "The pack recommends " + maxMemoryRecommended + "MB to run."
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

}
