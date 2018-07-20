package kaptainwutax.memorytester.utility;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import kaptainwutax.memorytester.thread.ThreadMain;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
@IFMLLoadingPlugin.TransformerExclusions("com.srkw.memorytester.")
public class PluginLoader implements IFMLLoadingPlugin {

	public static ThreadMain thread = new ThreadMain();
	
    public PluginLoader() {
    	
        thread.start();

        while (thread.isInMenu) {

            if (!thread.shouldGameStart) {
                Set<Thread> threads = Thread.getAllStackTraces().keySet();
                for (Thread t : threads) {if (t != Thread.currentThread()) t.stop();}
                Thread.currentThread().stop();
            }

            try {Thread.currentThread().sleep(100);} catch (InterruptedException e) {}
            
        }

    }

    @Override
    public String[] getASMTransformerClass() {return new String[0];}

    @Override
    public String getModContainerClass() {return null;}

    @Nullable
    @Override
    public String getSetupClass() {return null;}

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {return null;}
    
}