package com.srkw.memorytester.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.srkw.memorytester.thread.ThreadMain;

public class DataGeneral {
	
	private ThreadMain threadInstance;
	
	//DEFAULT VALUES
	private final int defaultRecommendedMemoryAllocation = 1024;
	private final long defaultMemoryTextUpdateDelay = 500;
	private final boolean defaultForceCrash = false;
	private final String defaultCrashInfo = "If you would like to play with a lower allocation, contact the pack maker to adjust the settings.";
	private final String defaultredirectCrashLink = "null";
	
	//DYNAMIC VALUES
	private int recommendedMemoryAllocation = 1024;
	private long memoryTextUpdateDelay = 500;
	private boolean forceCrash = false;
	private String crashInfo = "If you would like to play with a lower allocation, contact the pack maker to adjust the settings.";
	private String redirectCrashLink = "null";
	
	public DataGeneral(ThreadMain threadInstance) {
		this.threadInstance = threadInstance;
	}
	
	public void configLoadData() {
    	
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
                        + "RedirectCrashLink=" + redirectCrashLink + "\n"
                );
                writer.flush();
                writer.close();
                input = new FileReader("config/MemoryTester/general.txt");
            } catch (IOException e2) {threadInstance.inGuiErrorCrash("Unexpected error in " + "config/MemoryTester/general.txt");}

        }
        
        configParseData(input);

    }
    
    private void configParseData(FileReader input) {
    	
    	BufferedReader bufRead = new BufferedReader(input);
        Boolean illegalArgument = false;
        String[] data = null;
        
        try {String myLine = bufRead.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash("RecommendedMemoryAllocation entry in " + "config/MemoryTester/general.txt");}

            if (data[0].trim().contains("RecommendedMemoryAllocation")) {
            	recommendedMemoryAllocation = Integer.parseInt(data[1].trim());
            } else {threadInstance.inGuiErrorCrash("RecommendedMemoryAllocation entry in " + "config/MemoryTester/general.txt");}

        try {String myLine = bufRead.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash("MemoryTextUpdateDelay entry in " + "config/MemoryTester/general.txt");}

            if (data[0].trim().contains("MemoryTextUpdateDelay")) {
                memoryTextUpdateDelay = Integer.parseInt(data[1].trim());
            } else {threadInstance.inGuiErrorCrash("MemoryTextUpdateDelay entry in " + "config/MemoryTester/general.txt");}

        try {String myLine = bufRead.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash("ForceCrash entry in " + "config/MemoryTester/general.txt");}

            if (data[0].trim().contains("ForceCrash")) {
            	forceCrash = Boolean.parseBoolean(data[1].trim());
            } else {threadInstance.inGuiErrorCrash("ForceCrash entry in " + "config/MemoryTester/general.txt");}
        
        try {String myLine = bufRead.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash("CrashInfo entry in " + "config/MemoryTester/general.txt");}

            if (data[0].trim().contains("CrashInfo")) {
                crashInfo = data[1].trim();
            } else {threadInstance.inGuiErrorCrash("CrashInfo entry in " + "config/MemoryTester/general.txt");}
           
	    try {String myLine = bufRead.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash("RedirectCrashLink entry in " + "config/MemoryTester/general.txt");}
	    
            if (data[0].trim().contains("RedirectCrashLink")) {
            	redirectCrashLink = data[1].trim();
            } else {threadInstance.inGuiErrorCrash("RedirectCrashLink entry in " + "config/MemoryTester/general.txt");}
        
        try {bufRead.close();} catch (IOException e) {threadInstance.inGuiErrorCrash("Unexpected error while accessing the data, any other program accessing the directory?");}
        setParsedDataToThread();
        
    }
    
    private void setParsedDataToThread() {
    	threadInstance.recommendedMemoryAllocation = this.recommendedMemoryAllocation;
    	threadInstance.memoryTextUpdateDelay = this.memoryTextUpdateDelay;
    	threadInstance.forceCrash = this.forceCrash;
    	threadInstance.crashInfo = this.crashInfo;
    	threadInstance.redirectCrashLink = this.redirectCrashLink;
    }
    
    public void setDefaultDataToThread() {
    	recommendedMemoryAllocation = this.defaultRecommendedMemoryAllocation;
    	memoryTextUpdateDelay = this.defaultMemoryTextUpdateDelay;
    	forceCrash = this.defaultForceCrash;
    	crashInfo = this.defaultCrashInfo;
    	redirectCrashLink = this.defaultredirectCrashLink;
    	
    	resetFile(); 	
    	threadInstance.stop();
    	threadInstance = new ThreadMain();
    	threadInstance.start();
    }
    
    public void resetFile() {
    	try {
    		BufferedWriter writer = null;
        	File dir = new File("config/MemoryTester");
        	dir.mkdir();
            writer = new BufferedWriter(new FileWriter("config/MemoryTester/general.txt"));
            writer.write(
            		"RecommendedMemoryAllocation=" + recommendedMemoryAllocation + "\n"
                    + "MemoryTextUpdateDelay=" + memoryTextUpdateDelay + "\n"
                    + "ForceCrash=" + forceCrash + "\n"
                    + "CrashInfo=" + crashInfo + "\n"
                    + "RedirectCrashLink=" + redirectCrashLink + "\n"
            );
            writer.flush();
            writer.close();
        } catch (IOException e2) {
        	threadInstance.inGuiErrorCrash("Unexpected error while resetting, please delete the whole directory.");
        }
    }

}
