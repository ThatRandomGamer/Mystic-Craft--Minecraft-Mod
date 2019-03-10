package com.TRGamer28.coppermod.util.handlers;

import com.TRGamer28.coppermod.blocks.AlloyFurnace.TileEntityAlloyFurnace;

import net.minecraftforge.fml.common.registry.GameRegistry;


public class TileEntityHandler 
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityAlloyFurnace.class, "dual_furnace");
	
	}
}