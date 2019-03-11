package com.TRGamer28.coppermod.util.handlers;

import com.TRGamer28.coppermod.Main;
import com.TRGamer28.coppermod.blocks.AlloyFurnace.TileEntityAlloyFurnace;
import com.TRGamer28.coppermod.init.ModBlocks;
import com.TRGamer28.coppermod.init.ModItems;
import com.TRGamer28.coppermod.util.IHasModel;
import com.TRGamer28.coppermod.util.compat.OreDictionaryCompat;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler 
{
   @SubscribeEvent
   public static void onItemRegister(RegistryEvent.Register<Item>event)
   {
	   event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
   }
   
   @SubscribeEvent
   public static void onBlockRegister(RegistryEvent.Register<Block>event)
   {
	   event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	   TileEntityHandler.registerTileEntities();
   }
   
   @SubscribeEvent
   public static void onModelRegister(ModelRegistryEvent event)
   {
	   for (Item item : ModItems.ITEMS) 
	   {
		   if (item instanceof IHasModel)
		   {
			   ((IHasModel)item).registerModels();
			   
		   
		   }
	   }
	   for (Block block : ModBlocks.BLOCKS) 
	   {
		   if (block instanceof IHasModel)
		   {
			   ((IHasModel)block).registerModels();
			   
		   
		   }
	   
	   }
   
	   
   
   }
   
   //Initilization Registries
	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		
		ConfigHandler.registerConfig(event);
	}
	
	public static void initRegistries(FMLInitializationEvent event)
{
	NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	OreDictionaryCompat.registerOres();
}
	
	public static void postInitRegistries(FMLPostInitializationEvent event) {}

   
}
   
