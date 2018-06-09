package com.srkw.memorytester.handler;

import com.srkw.memorytester.utility.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.RangeInt;

@Config(modid = Reference.MOD_ID)
public class ConfigHandler {
	
    @Config.Comment("Recommended Memory Allocation in MB.")
    public static int recommendedMemoryAllocated = 1024;
    
}
