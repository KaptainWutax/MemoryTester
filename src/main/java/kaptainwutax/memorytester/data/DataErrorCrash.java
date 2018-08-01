package kaptainwutax.memorytester.data;

import kaptainwutax.memorytester.thread.ThreadMain;

public class DataErrorCrash extends DataConfig {
		
	public static String directory = "config/MemoryTester/";
	public static String location = "ErrorCrash.txt";
	
	public DataUnit errorInfo;
	
	public DataErrorCrash(ThreadMain threadInstance) {
		super(threadInstance, directory, location);
	}
	
	@Override
	public void populateFields() {
		this.errorInfo = new DataUnit("ErrorInfo", "string", "The game was force crashed because of an illegal configuration.");
		super.populateFields();
	}
	
	@Override
	public void populateConfig() {
		this.addEntry(this.errorInfo);
		super.populateConfig();
	}
	
	@Override
	public void updateThread() {
		threadInstance.errorInfo = this.errorInfo.getValue().toString();
	}

}
