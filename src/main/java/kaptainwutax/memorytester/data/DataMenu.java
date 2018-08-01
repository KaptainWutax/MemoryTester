package kaptainwutax.memorytester.data;

import kaptainwutax.memorytester.thread.ThreadMain;

public class DataMenu extends DataConfig {
	
	public static String directory = "config/MemoryTester/";
	public static String location = "Menu.txt";
	
	public DataUnit menuHeader;
	public DataUnit menuLine1WhenOptimalMemory;
	public DataUnit menuLine1WhenNonoptimalMemory;
	public DataUnit menuButtonStartText;
	public DataUnit menuButtonQuitText;
	public DataUnit menuButtonStartDebugText;
	
	public DataMenu(ThreadMain threadInstance) {
		super(threadInstance, directory, location);
	}
	
	@Override
	public void populateFields() {
		this.menuHeader = new DataUnit("Header", "string", "You have [AllocatedMemory]MB of memory allocated.");
		this.menuLine1WhenOptimalMemory = new DataUnit("Line1WhenOptimalMemory", "string", "The pack recommends [RecommendedMemory]MB to run.");
		this.menuLine1WhenNonoptimalMemory = new DataUnit("Line1WhenNonoptimalMemory", "string", "The pack recommends [RecommendedMemory]MB to run." + " Consider using [RecommendedMemory-AllocatedMemory]more.");
		this.menuButtonStartText = new DataUnit("ButtonStartText", "string", "Start");
		this.menuButtonQuitText = new DataUnit("ButtonQuitText", "string", "Quit");
		this.menuButtonStartDebugText = new DataUnit("ButtonStartDebugText", "string", "Start Debug");
		super.populateFields();
	}
	
	@Override
	public void populateConfig() {
		this.addEntry(this.menuHeader);
		this.addEntry(this.menuLine1WhenOptimalMemory);
		this.addEntry(this.menuLine1WhenNonoptimalMemory);
		this.addEntry(this.menuButtonStartText);
		this.addEntry(this.menuButtonQuitText);
		this.addEntry(this.menuButtonStartDebugText);
		super.populateConfig();
	}
	
	@Override
	public void updateThread() {
		threadInstance.menuHeader = this.menuHeader.getValue().toString();
		threadInstance.menuLine1WhenOptimalMemory = this.menuLine1WhenOptimalMemory.getValue().toString();
		threadInstance.menuLine1WhenNonoptimalMemory = this.menuLine1WhenNonoptimalMemory.getValue().toString();
		threadInstance.menuButtonStartText = this.menuButtonStartText.getValue().toString();
		threadInstance.menuButtonQuitText = this.menuButtonQuitText.getValue().toString();
		threadInstance.menuButtonStartDebugText = this.menuButtonStartDebugText.getValue().toString();
	}
	
}
