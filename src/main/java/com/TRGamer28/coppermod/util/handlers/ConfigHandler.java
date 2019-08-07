package com.TRGamer28.coppermod.util.handlers;

import java.io.File;

import com.TRGamer28.coppermod.Main;
import com.TRGamer28.coppermod.util.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

	public static Configuration config;
	
	public static  int GUI_ALLOY_FURNACE = 0;

	public static void init(File file) {
		
		config = new Configuration(file);
		String category;
		
		category = "ID's";
		config.addCustomCategoryComment(category, "Set id's for Entites and Guis");
		GUI_ALLOY_FURNACE = config.getInt("Alloy Furnace Gui Id", category, 0, 0, 100, "ID for the Alloy Furnace GUI");
	
		config.save();
	}
	
	public static void registerConfig(FMLPreInitializationEvent event) {
		Main.config = new File(event.getModConfigurationDirectory()+"/"+ Reference.MOD_ID);
		Main.config.mkdirs();
		init(new File(Main.config.getPath(), Reference.MOD_ID +".cfg"));
	}
}
