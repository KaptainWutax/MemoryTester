package kaptainwutax.memorytester.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import kaptainwutax.memorytester.thread.ThreadMain;

public class DataGeneral implements IDataHandler {
	
	private ThreadMain threadInstance;
	
	//DEFAULT VALUES
	private final int defaultRecommendedMemoryAllocation = 1024;
	private final boolean defaultUseMenuGui = true;
	private final boolean defaultUseStatisticsGui = true;
	private final boolean defaultUseForceCrashGui = false;
	private final String defaultCrashInfo = "If you would like to play with a lower allocation, contact the pack maker to adjust the settings.";
	private final String defaultredirectCrashLink = "null";
	
	//NAMES
	private final String nameRecommendedMemoryAllocation = "RecommendedMemoryAllocation";
	private final String nameUseMenuGui = "UseMenuGui";
	private final String nameUseStatisticsGui = "UseStatisticsGui";
	private final String nameUseForceCrashGui = "UseForceCrashGui";
	
	//DYNAMIC VALUES
	private int recommendedMemoryAllocation = defaultRecommendedMemoryAllocation;
	private boolean useMenuGui = defaultUseMenuGui;
	private boolean useStatisticsGui = defaultUseStatisticsGui;
	private boolean useForceCrashGui = defaultUseForceCrashGui;
	private String crashInfo = defaultCrashInfo;
	private String redirectCrashLink = defaultredirectCrashLink;
	
	//CONFIG
	public String directory = "config/MemoryTester";
	public String location = "config/MemoryTester/general.txt";
	public String defaultConfig = nameRecommendedMemoryAllocation + "=" + defaultRecommendedMemoryAllocation + "\n"
								+ nameUseMenuGui + "=" +  defaultUseMenuGui + "\n"
								+ nameUseStatisticsGui + "=" + defaultUseStatisticsGui + "\n"
								+ nameUseForceCrashGui + "=" + defaultUseForceCrashGui + "\n";
	
	public DataGeneral(ThreadMain threadInstance) {
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
        
        try {String myLine = bufReader.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash(nameRecommendedMemoryAllocation + " entry in " + location);}

            if (data[0].trim().contains(nameRecommendedMemoryAllocation)) {
            	recommendedMemoryAllocation = Integer.parseInt(data[1].trim());
            } else {threadInstance.inGuiErrorCrash(nameRecommendedMemoryAllocation + " entry in " + location);}

        try {String myLine = bufReader.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash(nameUseMenuGui + " entry in " + location);}

        	if (data[0].trim().contains(nameUseMenuGui)) {
        		useMenuGui = Boolean.parseBoolean(data[1].trim());
        	} else {threadInstance.inGuiErrorCrash(nameUseMenuGui + " entry in " + location);}
        
        try {String myLine = bufReader.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash(nameUseStatisticsGui + " entry in " + location);}

    		if (data[0].trim().contains(nameUseStatisticsGui)) {
    			useStatisticsGui = Boolean.parseBoolean(data[1].trim());
    		} else {threadInstance.inGuiErrorCrash(nameUseStatisticsGui + " entry in " + location);}
 
        try {String myLine = bufReader.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash(nameUseForceCrashGui + " entry in " + location);}

            if (data[0].trim().contains(nameUseForceCrashGui)) {
            	useForceCrashGui = Boolean.parseBoolean(data[1].trim());
            } else {threadInstance.inGuiErrorCrash(nameUseForceCrashGui + " entry in " + location);}       
        
        try {bufReader.close();} catch (IOException e) {threadInstance.inGuiErrorCrash("Unexpected error while accessing the data, any other program accessing the directory?");}
        setParsedDataToThread();
        
    }
    
    @Override
    public void setParsedDataToThread() {
    	threadInstance.recommendedMemoryAllocation = this.recommendedMemoryAllocation;
    	threadInstance.useMenuGui = this.useMenuGui;
    	threadInstance.useForceCrashGui = this.useForceCrashGui;
    }
    
    @Override
    public void setDefaultDataToThread() {
    	recommendedMemoryAllocation = this.defaultRecommendedMemoryAllocation;
    	useMenuGui = this.defaultUseMenuGui;
    	useForceCrashGui = this.defaultUseForceCrashGui;  	
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
