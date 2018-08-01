package kaptainwutax.memorytester.data;

import kaptainwutax.memorytester.thread.ThreadMain;

public class DataGeneral extends DataConfig {

	public static String directory = "config/MemoryTester/";
	public static String location = "General.txt";
	
	public DataUnit recommendedMemoryAllocation;
	public DataUnit useMenuGui;
	public DataUnit useStatisticsGui;
	public DataUnit useForceCrashGui;
	
	public DataGeneral(ThreadMain threadInstance) {
		super(threadInstance, directory, location);
	}
	
	@Override
	public void populateFields() {
		this.recommendedMemoryAllocation = new DataUnit("RecommendedMemoryAllocation", "int", 1024);
		this.useMenuGui = new DataUnit("UseMenuGui", "bool", true);
		this.useStatisticsGui = new DataUnit("UseStatisticsGui", "bool", true);
		this.useForceCrashGui = new DataUnit("UseForceCrashGui", "bool", false);
		super.populateFields();
	}
	
	@Override
	public void populateConfig() {
		this.addEntry(this.recommendedMemoryAllocation);
		this.addEntry(this.useMenuGui);
		this.addEntry(this.useStatisticsGui);
		this.addEntry(this.useForceCrashGui);
		super.populateConfig();
	}
	
	@Override
	public void updateThread() {
		threadInstance.recommendedMemoryAllocation = (int)this.recommendedMemoryAllocation.getValue();
		threadInstance.useMenuGui = Boolean.parseBoolean(this.useMenuGui.getValue().toString());
		threadInstance.useStatisticsGui = Boolean.parseBoolean(this.useStatisticsGui.getValue().toString());
		threadInstance.useForceCrashGui = Boolean.parseBoolean(this.useForceCrashGui.getValue().toString());
	}
	
}
