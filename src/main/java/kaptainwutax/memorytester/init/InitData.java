package kaptainwutax.memorytester.init;

import java.util.ArrayList;

import kaptainwutax.memorytester.data.DataConfig;
import kaptainwutax.memorytester.data.DataErrorCrash;
import kaptainwutax.memorytester.data.DataForceCrash;
import kaptainwutax.memorytester.data.DataGeneral;
import kaptainwutax.memorytester.data.DataMenu;
import kaptainwutax.memorytester.data.DataStatistics;
import kaptainwutax.memorytester.thread.ThreadMain;
import kaptainwutax.memorytester.utility.PluginLoader;

public class InitData {
	
	public static ThreadMain thread = PluginLoader.thread;
	public static ArrayList<DataConfig> configs = new ArrayList<DataConfig>();
	
	public static DataGeneral dataGeneral = new DataGeneral(thread);
	public static DataMenu dataMenu = new DataMenu(thread);
	public static DataStatistics dataDebug = new DataStatistics(thread);
	public static DataForceCrash dataForceCrash = new DataForceCrash(thread);
	public static DataErrorCrash dataErrorCrash = new DataErrorCrash(thread); 

	
	public static void initializeConfigs() {
		configs.add(dataGeneral);
		configs.add(dataMenu);
		configs.add(dataDebug);
		configs.add(dataForceCrash);
		configs.add(dataErrorCrash);
		for(DataConfig config : configs) {
			config.setDefaultDataToThread();
			config.configLoadData();
			System.out.println(config.toString());
		}
	}
	
}
