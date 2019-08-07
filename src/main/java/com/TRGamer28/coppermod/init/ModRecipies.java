package com.TRGamer28.coppermod.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipies {

	public static void init() {
		GameRegistry.addSmelting(ModBlocks.COPPER_ORE, new ItemStack(ModItems.COPPER_INGOT, 1), 2.0f);
		GameRegistry.addSmelting(ModBlocks.TIN_ORE, new ItemStack(ModItems.TIN_INGOT, 1), 2.0f);
		GameRegistry.addSmelting(ModBlocks.SILVER_ORE, new ItemStack(ModItems.SILVER_INGOT, 1), 2.0f);
		GameRegistry.addSmelting(ModBlocks.LIMESTONE, new ItemStack(ModBlocks.MARBLE, 1), 2.0f);
		GameRegistry.addSmelting(ModBlocks.CARBON_ORE, new ItemStack(ModItems.CARBON_CRYSTAL, 1), 2.0f);
		GameRegistry.addSmelting(Items.WATER_BUCKET, new ItemStack(ModItems.BOILED_WATER, 1), 2.0f);
		GameRegistry.addSmelting(ModItems.CORN, new ItemStack(ModItems.CORN_COOKED, 1), 2.0f);
		GameRegistry.addSmelting(ModItems.CORN_COB, new ItemStack(ModItems.CORN_COB_COOKED, 1), 2.0f);




		
		
	}
	
}
