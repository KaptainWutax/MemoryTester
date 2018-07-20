package kaptainwutax.memorytester.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import kaptainwutax.memorytester.thread.ThreadMain;

public class DataForceCrash implements IDataHandler {

	private ThreadMain threadInstance;
	
	//DEFAULT VALUES
	private final String defaultInfo = "If you would like to play with a lower allocation, contact the pack maker to adjust the settings.";
	private final String defaultRedirectLink = "null";
	
	//NAMES 
	private final String nameInfo = "Info";
	private final String nameRedirectLink = "RedirectLink";
	
	//DYNAMIC VALUES
	private String info = defaultInfo;
	private String redirectLink = defaultRedirectLink;
	
	//CONFIG
	public String directory = "config/MemoryTester";
	public String location = "config/MemoryTester/forceCrash.txt";
	public String defaultConfig = nameInfo + "=" + defaultInfo + "\n"
								+ nameRedirectLink + "=" + redirectLink + "\n";
	
	public DataForceCrash(ThreadMain threadInstance) {
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
        
        try {String myLine = bufReader.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash(nameInfo + "entry in " + location);}

            if (data[0].trim().contains(nameInfo)) {
            	info = data[1].trim();
            } else {threadInstance.inGuiErrorCrash(nameInfo + " entry in " + location);}
            
        try {String myLine = bufReader.readLine(); data = myLine.split("=");} catch (Exception e) {threadInstance.inGuiErrorCrash(nameRedirectLink + "entry in " + location);}

        	if (data[0].trim().contains(nameRedirectLink)) {
        		redirectLink = data[1].trim();
        	} else {threadInstance.inGuiErrorCrash(nameRedirectLink + " entry in " + location);}
        
        try {bufReader.close();} catch (IOException e) {threadInstance.inGuiErrorCrash("Unexpected error while accessing the data, any other program using the directory?");}
        setParsedDataToThread();
        
    }
    
    @Override
    public void setParsedDataToThread() {
    	threadInstance.info = this.info;
    	threadInstance.redirectLink = this.redirectLink;
    }
    
    @Override
    public void setDefaultDataToThread() {
    	info = this.defaultInfo;
    	redirectLink = this.defaultRedirectLink;	
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
