package com.TRGamer28.coppermod;

import java.io.File;

import com.TRGamer28.coppermod.blocks.AlloyFurnace.BlockAlloyFurnace;
import com.TRGamer28.coppermod.blocks.AlloyFurnace.TileEntityAlloyFurnace;
import com.TRGamer28.coppermod.init.ModRecipies;
import com.TRGamer28.coppermod.proxy.CommonProxy;
import com.TRGamer28.coppermod.util.Reference;
import com.TRGamer28.coppermod.util.handlers.GuiHandler;
import com.TRGamer28.coppermod.util.handlers.RegistryHandler;
import com.TRGamer28.coppermod.world.ModWorldGen;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static File config;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event){RegistryHandler.preInitRegistries(event);}
	{

		GameRegistry.registerWorldGenerator(new ModWorldGen(), 3);
		

	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		ModRecipies.init();
		RegistryHandler.initRegistries(event);
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		RegistryHandler.postInitRegistries(event);
	}
}