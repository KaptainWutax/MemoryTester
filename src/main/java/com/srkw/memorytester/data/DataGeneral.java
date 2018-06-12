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
	private int defaultRecommendedMemoryAllocation = 1024;
	private long defaultMemoryTextUpdateDelay = 500;
	private boolean defaultForceCrash = false;
	private String defaultCrashInfo = "If you would like to play with a lower allocation, contact the pack maker to adjust the settings.";
	
	//DYNAMIC VALUES
	private int recommendedMemoryAllocation = 1024;
	private long memoryTextUpdateDelay = 500;
	private boolean forceCrash = false;
	private String crashInfo = "If you would like to play with a lower allocation, contact the pack maker to adjust the settings.";
	    
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
                );
                writer.flush();
                writer.close();
                input = new FileReader("config/MemoryTester/general.txt");
            } catch (IOException e2) {threadInstance.inGuiErrorCrash("Unexpected error in " + "config/MemoryTester/general.txt");}

        }
        
        try {configParseData(input);} catch (IOException e) {threadInstance.inGuiErrorCrash("ForceCrash entry in " + "config/MemoryTester/general.txt");}

    }
    
    private void configParseData(FileReader input) throws IOException {
    	
    	BufferedReader bufRead = new BufferedReader(input);

        String myLine = bufRead.readLine();
        String[] data = myLine.split("=");
        Boolean illegalArgument = false;

            if (data[0].trim().contains("RecommendedMemoryAllocation")) {
            	recommendedMemoryAllocation = Integer.parseInt(data[1].trim());
            } else {threadInstance.inGuiErrorCrash("RecommendedMemoryAllocation entry in " + "config/MemoryTester/general.txt");}

        myLine = bufRead.readLine();
        data = myLine.split("=");

            if (data[0].trim().contains("MemoryTextUpdateDelay")) {
                memoryTextUpdateDelay = Integer.parseInt(data[1].trim());
            } else {threadInstance.inGuiErrorCrash("MemoryTextUpdateDelay entry in " + "config/MemoryTester/general.txt");}

        myLine = bufRead.readLine();
        data = myLine.split("=");

            if (data[0].trim().contains("ForceCrash")) {
            	forceCrash = Boolean.parseBoolean(data[1].trim());
            } else {threadInstance.inGuiErrorCrash("ForceCrash entry in " + "config/MemoryTester/general.txt");}
        
        myLine = bufRead.readLine();
        data = myLine.split("=");

            if (data[0].trim().contains("CrashInfo")) {
                crashInfo = data[1].trim();
            } else {threadInstance.inGuiErrorCrash("CrashInfo entry in " + "config/MemoryTester/general.txt");}

        bufRead.close();
        setParsedDataToThread();
        
    }
    
    private void setParsedDataToThread() {
    	threadInstance.recommendedMemoryAllocation = this.recommendedMemoryAllocation;
    	threadInstance.memoryTextUpdateDelay = this.memoryTextUpdateDelay;
    	threadInstance.forceCrash = this.forceCrash;
    	threadInstance.crashInfo = this.crashInfo;
    }
    
    public void setDefaultDataToThread() {
    	recommendedMemoryAllocation = this.defaultRecommendedMemoryAllocation;
    	memoryTextUpdateDelay = this.defaultMemoryTextUpdateDelay;
    	forceCrash = this.defaultForceCrash;
    	crashInfo = this.defaultCrashInfo;
    	
    	if(resetFile()) {  	
    		threadInstance.stop();
    		threadInstance = new ThreadMain();
    		threadInstance.start();
    	}
    }
    
    public boolean resetFile() {
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
            );
            writer.flush();
            writer.close();
        } catch (IOException e2) {
        	threadInstance.inGuiErrorCrash("Unexpected error while resetting, please delete the whole directory.");
        	return false;
        }
    	return true;
    }

}
