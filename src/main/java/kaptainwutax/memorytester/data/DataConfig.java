package kaptainwutax.memorytester.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import kaptainwutax.memorytester.thread.ThreadMain;

public class DataConfig {
	
	public ThreadMain threadInstance;
	
	public String directory = "";
	public String location = directory + "";
	
	public String defaultConfig = "";
	private ArrayList<DataUnit> entries = new ArrayList<DataUnit>();
	
	public DataConfig(ThreadMain threadInstance, String directory, String location) {
		this.threadInstance = threadInstance;
		this.directory = directory;
		this.location = this.directory + location;
		populateFields();
		populateConfig();
		setDefaultDataToThread();
	}
	
	public void populateFields() {
		
	}
	
	public void populateConfig() {
		for(DataUnit entry : entries) {
			defaultConfig = defaultConfig + entry.getName() + "=" + entry.getDefaultValue() + "\n";
		}
	}
	
	public DataUnit[] getEntries() {
		return entries.toArray(new DataUnit[entries.size()]);
	}
	
	public void addEntry(DataUnit data) {
		entries.add(data);
	}
	
	public void configLoadData() {
    	
        FileReader reader = null;
        BufferedWriter writer = null;
        
        try {
        	reader = new FileReader(this.location);
        } catch (FileNotFoundException e) {     	
            try {
            	File dir = new File(this.directory);
            	dir.mkdir();
                writer = new BufferedWriter(new FileWriter(this.location));
                writer.write(this.defaultConfig);
                writer.flush();
                writer.close();
                reader = new FileReader(this.location);
            } catch (IOException e2) {runUnexpectedLoadingError(this.location);}

        }
        
        configParseData(reader);

    }
	
    private void configParseData(FileReader input) {
    	
    	BufferedReader bufReader = new BufferedReader(input);
        Boolean illegalArgument = false;
        String[] data = null;
        
        for(DataUnit entry : this.entries) {
	        try {
	        	String myLine = bufReader.readLine(); 
	        	data = myLine.split("=");
	        } catch (Exception e) {
	        	runEntryError(entry.getName(), this.location);
	        }
	
            if (data[0].trim().equals(entry.getName())) {
            	try {
	        		switch(entry.getType()) {
	        		case "int" : entry.setValue(Integer.parseInt(data[1])); break;
	        		case "bool" : entry.setValue(Boolean.parseBoolean(data[1])); break;
	        		case "string" : entry.setValue(data[1]); break;
	        		}
            	} catch(Exception e) {
            		runEntryError(entry.getName(), this.location);
            	}     		
            } else {
            	runEntryError(entry.getName(), this.location);
            }
        }
        
        try {bufReader.close();} catch (IOException e) {runUnexpectedLoadingError(this.location);}
        updateThread();
        
    }
    
    public void updateThread() {
    }

    public void setDefaultDataToThread() {
    	for(DataUnit entry : this.entries) {
    		switch(entry.getType()) {
    		case "int" : entry.setValue((int)entry.getDefaultValue()); break;
    		case "bool" : entry.setValue((boolean)entry.getDefaultValue()); break;
    		case "string" : entry.setValue((String)entry.getDefaultValue()); break;
    		}
    	}
    	updateThread();
    }
    
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
        	runUnexpectedResettingError();
        }
    }
    
    public void runUnexpectedLoadingError(String location) {
    	threadInstance.inGuiErrorCrash("Unexpected error while accessing the data in " + location + ", any other program using the directory?");
    }
    
    public void runUnexpectedParsingError(String location) {
    	threadInstance.inGuiErrorCrash("Unknown error trying to generate config in " + location);
    }
    
    public void runUnexpectedResettingError() {
    	threadInstance.inGuiErrorCrash("Unexpected error while resetting, please delete the whole directory.");
    }
    
    public void runEntryError(String name, String location) {
    	threadInstance.inGuiErrorCrash(name + " entry in " + location);
    }

    @Override
    public String toString() {
    	String string = "DataConfig{";
    	for(int i = 0 ; i < entries.size() ; i++) {
    		if(i != entries.size() - 1) {
    			string = string + entries.get(i).getName() + ", ";
    		} else {
    			string = string + entries.get(i).getName() + "}";
    		}
    	} 
    	string = string + " at " + location;
		return string;
    }
    
}
