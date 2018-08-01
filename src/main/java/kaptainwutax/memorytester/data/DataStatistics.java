package kaptainwutax.memorytester.data;

import kaptainwutax.memorytester.thread.ThreadMain;

public class DataStatistics extends DataConfig {
	
	public static String directory = "config/MemoryTester/";
	public static String location = "Statistics.txt";
	
	public DataUnit memoryUsageUpdateDelay;
	
	public DataStatistics(ThreadMain threadInstance) {
		super(threadInstance, directory, location);
	}
	
	@Override
	public void populateFields() {
		this.memoryUsageUpdateDelay = new DataUnit("MemoryUsageUpdateDelay", "int", 500);
		super.populateFields();
	}
	
	@Override
	public void populateConfig() {
		this.addEntry(this.memoryUsageUpdateDelay);
		super.populateConfig();
	}
	
	@Override
	public void updateThread() {
		threadInstance.memoryUsageUpdateDelay = (int)this.memoryUsageUpdateDelay.getValue();
	}

}

