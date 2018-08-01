package kaptainwutax.memorytester;

import kaptainwutax.memorytester.utility.Constant;
import kaptainwutax.memorytester.utility.PluginLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constant.MOD_ID, name = Constant.NAME, version = Constant.VERSION)
public class MemoryTester {

    @Instance
    public static MemoryTester instance;
    
    @EventHandler
    public static void construction(FMLConstructionEvent event) {
    	if(!PluginLoader.thread.shouldGameStart) {
        	FMLCommonHandler.instance().exitJava(0, false);
    	}
    }
    
    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {}

    @EventHandler
    public static void init(FMLInitializationEvent event) {}

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
    	PluginLoader.thread.hasGameInitialized = true;
    	PluginLoader.thread.interrupt();
    }

}

