package com.TRGamer28.coppermod.util.compat;

import com.TRGamer28.coppermod.init.ModBlocks;
import com.TRGamer28.coppermod.init.ModItems;

import net.minecraftforge.oredict.*;

public class OreDictionaryCompat 
{
   public static void registerOres()
   {
	   OreDictionary.registerOre("oreCopper", ModBlocks.COPPER_ORE);
	   OreDictionary.registerOre("oreSilver", ModBlocks.SILVER_ORE);
	   OreDictionary.registerOre("oreCarbon", ModBlocks.CARBON_ORE);
	   OreDictionary.registerOre("oreTin", ModBlocks.TIN_ORE);
	   OreDictionary.registerOre("ingotCopper", ModItems.COPPER_INGOT);
	   OreDictionary.registerOre("ingotSilver", ModItems.SILVER_INGOT);
	   OreDictionary.registerOre("ingotCarbon", ModItems.CARBON_CRYSTAL);
	   OreDictionary.registerOre("ingotTin", ModItems.TIN_INGOT);

   }

}
