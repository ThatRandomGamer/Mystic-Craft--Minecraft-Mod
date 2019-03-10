package com.TRGamer28.coppermod.util.handlers;

import com.TRGamer28.coppermod.blocks.AlloyFurnace.ContainerAlloyFurnace;
import com.TRGamer28.coppermod.blocks.AlloyFurnace.GuiAlloyFurnace;
import com.TRGamer28.coppermod.blocks.AlloyFurnace.GuiAlloyFurnace;
import com.TRGamer28.coppermod.blocks.AlloyFurnace.TileEntityAlloyFurnace;
import com.TRGamer28.coppermod.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


public class GuiHandler implements IGuiHandler
 
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == ConfigHandler.GUI_ALLOY_FURNACE) return new ContainerAlloyFurnace(player.inventory, (TileEntityAlloyFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == ConfigHandler.GUI_ALLOY_FURNACE) return new GuiAlloyFurnace(player.inventory, (TileEntityAlloyFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}

}

