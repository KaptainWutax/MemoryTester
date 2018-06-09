package com.srkw.memorytester;

import com.srkw.memorytester.thread.ThreadMain;
import com.srkw.memorytester.utility.*;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class MemoryTester {

	@Instance
	public static MemoryTester instance;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {new Thread(new ThreadMain()).start();}
	@EventHandler
	public static void init(FMLInitializationEvent event) {}
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {}
	
}

