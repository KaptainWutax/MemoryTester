package kaptainwutax.memorytester.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import kaptainwutax.memorytester.thread.ThreadMain;

public class DataStatistics implements IDataHandler {
	
	private ThreadMain threadInstance;
	
	//DEFAULT VALUES
	private final long defaultMemoryUsageUpdateDelay = 500; 
	
	//NAMES 
	private final String nameMemoryUsageUpdateDelay = "MemoryUsageUpdateDelay";
	
	//DYNAMIC VALUES
	private long memoryUsageUpdateDelay  = defaultMemoryUsageUpdateDelay;
	
	//CONFIG
	public String directory = "config/MemoryTester";
	public String location = "config/MemoryTester/statistics.txt";
	public String defaultConfig = nameMemoryUsageUpdateDelay + "=" + defaultMemoryUsageUpdateDelay + "\n";
	
	public DataStatistics(ThreadMain threadInstance) {
		this.threadInstance = threadInstance;
	}
	
	@Override
	public void configLoadData() {
    	
        FileReader reader = null;
        BufferedWriter writer = null;
        
        try {reader = new FileReader(location);} 
        catch (FileNotFoundException e) {     	
            try {
            	File dir = new File(directory);
            	dir.mkdir();
                writer = new BufferedWriter(new FileWriter(location));
                writer.write(defaultConfig);
                writer.flush();
                writer.close();
                reader = new FileReader(location);
            } catch (IOException e2) {threadInstance.inGuiErrorCrash("Unexpected and unknown error in " + location);}

        }
        
        configParseData(reader);

    }
    
    private void configParseData(FileReader input) {
    	
    	BufferedReader bufReader = new BufferedReader(input);
        Boolean illegalArgument = false;
        String[] data = null;
        
        try {String myLine = bufReader.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash(nameMemoryUsageUpdateDelay + "entry in " + location);}

            if (data[0].trim().contains(nameMemoryUsageUpdateDelay)) {
            	memoryUsageUpdateDelay = Long.parseLong(data[1].trim());
            } else {threadInstance.inGuiErrorCrash(nameMemoryUsageUpdateDelay + " entry in " + location);}
        
        try {bufReader.close();} catch (IOException e) {threadInstance.inGuiErrorCrash("Unexpected error while accessing the data, any other program using the directory?");}
        setParsedDataToThread();
        
    }
    
    @Override
    public void setParsedDataToThread() {
    	threadInstance.memoryUsageUpdateDelay = this.memoryUsageUpdateDelay;
    }
    
    @Override
    public void setDefaultDataToThread() {
    	memoryUsageUpdateDelay = this.defaultMemoryUsageUpdateDelay;   	
    	resetFile(); 
    	setParsedDataToThread();
    }
    
    @Override
    public void resetFile() {
    	try {
    		BufferedWriter writer = null;
        	File dir = new File(directory);
        	dir.mkdir();
            writer = new BufferedWriter(new FileWriter(location));
            writer.write(defaultConfig);
            writer.flush();
            writer.close();
        } catch (IOException e2) {
        	threadInstance.inGuiErrorCrash("Unexpected error while resetting, please delete the whole directory.");
        }
    }

}

