package com.srkw.memorytester.utility;

import com.srkw.memorytester.thread.ThreadMain;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
@IFMLLoadingPlugin.TransformerExclusions("com.srkw.memorytester.")
public class PluginLoader implements IFMLLoadingPlugin {

    //private static final Logger log = LogManager.getLogger();

    public PluginLoader() {

        ThreadMain thread = new ThreadMain();
        thread.start();

        while (thread.isInMenu) {

            if (!thread.shouldGameStart) {

                Set<Thread> threads = Thread.getAllStackTraces().keySet();
                for (Thread t : threads) {
                    if (t != Thread.currentThread())
                        t.stop();
                }

                Thread.currentThread().stop();
            }

            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}