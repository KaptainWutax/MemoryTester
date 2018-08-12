package kaptainwutax.memorytester.thread;

import java.util.ArrayList;

import kaptainwutax.memorytester.gui.GuiErrorCrash;
import kaptainwutax.memorytester.gui.GuiForceCrash;
import kaptainwutax.memorytester.gui.GuiMain;
import kaptainwutax.memorytester.gui.GuiMenu;
import kaptainwutax.memorytester.init.InitData;

public class ThreadMain extends Thread {
	
	//GUI
	public GuiMenu guiMenuInstance;
	public GuiMain guiMainInstance;
	public GuiForceCrash guiForceCrashInstance;
	public GuiErrorCrash guiErrorCrashInstance;
      
    //DATA VALUES
    //DataGeneral
    public int recommendedMemoryAllocation;
    public boolean useMenuGui;
    public boolean useStatisticsGui;
    public boolean useForceCrashGui;
    //DataMenu
    public String menuHeader;
    public String menuLine1WhenOptimalMemory;
    public String menuLine1WhenNonoptimalMemory;
    public String menuButtonStartText;
    public String menuButtonQuitText;
    public String menuButtonStartDebugText;
    //DataStatistics
    public long memoryUsageUpdateDelay;
    //DataForceCrash
    public String crashInfoHeader;
    public String crashInfoLine1;
    public String crashInfoLine2;
    public String crashInfoFooter;
    public String crashRedirectLink;
    //DataErrorCrash
    public String errorInfo;
    
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
        
        InitData.initializeConfigs();  
        
        if (recommendedMemoryAllocation > maxMemory && useForceCrashGui) {inGuiForceCrashAction();}
        
        while (true) { 
            if (isInMenu && useMenuGui) {
            	inGuiMenuAction();
            } else {
            	isInMenu = false;
            	if(!useStatisticsGui) {
            		return;
            	}
                while(!hasGameInitialized) {
                	performSleep(Long.MAX_VALUE);
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
                applyString(menuHeader)
        );

        if (recommendedMemoryAllocation > maxMemory) {
            guiMenuInstance.memoryAllocatedRecommendedText.setText(
            		applyString(menuLine1WhenNonoptimalMemory)
            );
        } else {
            guiMenuInstance.memoryAllocatedRecommendedText.setText(
                    applyString(menuLine1WhenOptimalMemory)
            );
        }
        
        if(guiMenuInstance.startButton != null) {guiMenuInstance.startButton.setText(applyString(menuButtonStartText));}
        if(guiMenuInstance.quitButton != null) {guiMenuInstance.quitButton.setText(applyString(menuButtonQuitText));}
        if(guiMenuInstance.debugButton != null) {guiMenuInstance.debugButton.setText(applyString(menuButtonStartDebugText));}

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
                    "No memory spikes were recorded."
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
        		applyString(crashInfoHeader)
        );
        guiForceCrashInstance.currentAllocation.setText(
        		applyString(crashInfoLine1)
        );
        guiForceCrashInstance.recommendedAllocation.setText(
        		applyString(crashInfoLine2)
        );
        guiForceCrashInstance.crashInfo.setText(
        		applyString(crashInfoFooter)
        );
        
    }
    
    private void updateGuiErrorCrash(String errorLocation) {
    	
    	guiErrorCrashInstance.crash.setText(
    			errorInfo
    	);
    	
    	guiErrorCrashInstance.crashInfo.setText(
    			errorLocation
    	);
    	
    }
    
    private String applyString(String string) {
    	String modifiedString = string;
    	modifiedString = modifiedString.replace("[AllocatedMemory]", String.valueOf(maxMemory));
    	modifiedString = modifiedString.replace("[RecommendedMemory]", String.valueOf(recommendedMemoryAllocation));
    	modifiedString = modifiedString.replace("[RecommendedMemory-AllocatedMemory]", String.valueOf(recommendedMemoryAllocation - maxMemory));
    	return modifiedString;
    }
    
    private String applyStringError(String string, String errorLocation) {
    	return null;
    }

}
