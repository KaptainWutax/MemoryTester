package kaptainwutax.memorytester.thread;

import java.util.ArrayList;

import kaptainwutax.memorytester.data.DataForceCrash;
import kaptainwutax.memorytester.data.DataGeneral;
import kaptainwutax.memorytester.data.DataStatistics;
import kaptainwutax.memorytester.data.IDataHandler;
import kaptainwutax.memorytester.gui.GuiErrorCrash;
import kaptainwutax.memorytester.gui.GuiForceCrash;
import kaptainwutax.memorytester.gui.GuiMain;
import kaptainwutax.memorytester.gui.GuiMenu;

public class ThreadMain extends Thread {

	//GUI
	public GuiMenu guiMenuInstance;
	public GuiMain guiMainInstance;
	public GuiForceCrash guiForceCrashInstance;
	public GuiErrorCrash guiErrorCrashInstance;
    
    //DATA OBJECTS
    public ArrayList<IDataHandler> data = new ArrayList<IDataHandler>();
      
    //DATA VALUES
    //DataGeneral
    public int recommendedMemoryAllocation;
    public boolean useMenuGui;
    public boolean useStatisticsGui;
    public boolean useForceCrashGui;
    //DataStatistics
    public long memoryUsageUpdateDelay;
    //DataForceCrash
    public String info;
    public String redirectLink;
    
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
        
        if (recommendedMemoryAllocation > maxMemory && useForceCrashGui) {inGuiForceCrashAction();}
        
        while (true) { 
            if (isInMenu && useMenuGui) {
            	inGuiMenuAction();
            } else {
            	isInMenu = false;
            	if(!useStatisticsGui) {
            		Thread.currentThread().stop();
            	}
                while(!hasGameInitialized) {
                	performSleep(100000000);
                }
            	inGuiMainAction();
            }
            performSleep(memoryUsageUpdateDelay);
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
    	data.add(new DataGeneral((ThreadMain) Thread.currentThread()));
    	data.add(new DataStatistics((ThreadMain) Thread.currentThread()));
    	data.add(new DataForceCrash((ThreadMain) Thread.currentThread()));
    	for(IDataHandler dataObj : data) {
    		dataObj.configLoadData();
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
        while (true) {performSleep(memoryUsageUpdateDelay);}
    }
    
    
    public void inGuiErrorCrash(String errorLocation) {
    	if(guiErrorCrashInstance == null) {
    		guiErrorCrashInstance = new GuiErrorCrash((ThreadMain) Thread.currentThread());
    		guiErrorCrashInstance.frame.setVisible(true);
    	}
    	updateGuiErrorCrash(errorLocation);
    	while (true) {performSleep(memoryUsageUpdateDelay);}
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
                    "The pack recommends " + recommendedMemoryAllocation + "MB to run." + " Consider using " + (recommendedMemoryAllocation - maxMemory) + " more."
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
                "The game was force crashed because of an insufficient memory allocation."
        );
        guiForceCrashInstance.currentAllocation.setText(
                "Your current memory allocation is " + maxMemory + "MB."
        );
        guiForceCrashInstance.recommendedAllocation.setText(
                "Please allocate " + recommendedMemoryAllocation + "MB before running."
        );
        guiForceCrashInstance.crashInfo.setText(
                info
        );
        
    }
    
    private void updateGuiErrorCrash(String errorLocation) {
    	
    	guiErrorCrashInstance.crash.setText(
    			"The game was force crashed because of an illegal configuration."
    	);
    	
    	guiErrorCrashInstance.crashInfo.setText(
    			errorLocation
    	);
    	
    }

}
