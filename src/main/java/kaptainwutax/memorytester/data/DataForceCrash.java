package kaptainwutax.memorytester.data;

import kaptainwutax.memorytester.thread.ThreadMain;

public class DataForceCrash extends DataConfig {
	
	public static String directory = "config/MemoryTester/";
	public static String location = "ForceCrash.txt";
	
	private DataUnit crashInfoHeader;
	private DataUnit crashInfoline1;
	private DataUnit crashInfoLine2;
	private DataUnit crashInfoFooter;
	private DataUnit crashRedirectLink;
	
	public DataForceCrash(ThreadMain threadInstance) {
		super(threadInstance, directory, location);
	}
	
	@Override
	public void populateFields() {
		this.crashInfoHeader = new DataUnit("InfoHeader", "string", "The game was force crashed because of an insufficient memory allocation.");
		this.crashInfoline1 = new DataUnit("InfoLine1", "string", "Your current memory allocation is [AllocatedMemory]MB.");
		this.crashInfoLine2 = new DataUnit("InfoLine2", "string", "Please allocate [RecommendedMemory]MB before running.");
		this.crashInfoFooter = new DataUnit("InfoFooter", "string", "If you would like to play with a lower allocation, contact the pack maker to adjust the settings.");
		this.crashRedirectLink = new DataUnit("RedirectLink", "string", "null");
		super.populateFields();
	}
	
	@Override
	public void populateConfig() {
		this.addEntry(crashInfoHeader);
		this.addEntry(crashInfoline1);
		this.addEntry(crashInfoLine2);
		this.addEntry(this.crashInfoFooter);
		this.addEntry(this.crashRedirectLink);
		super.populateConfig();
	}
	
	@Override
	public void updateThread() {
		threadInstance.crashInfoHeader = this.crashInfoHeader.getValue().toString();
		threadInstance.crashInfoLine1 = this.crashInfoline1.getValue().toString();
		threadInstance.crashInfoLine2 = this.crashInfoLine2.getValue().toString();
		threadInstance.crashInfoFooter = this.crashInfoFooter.getValue().toString();
		threadInstance.crashRedirectLink = this.crashRedirectLink.getValue().toString();
	}
	
}
