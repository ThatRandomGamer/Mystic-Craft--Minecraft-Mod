package com.TRGamer28.coppermod.blocks.AlloyFurnace.slots;
import com.TRGamer28.coppermod.blocks.AlloyFurnace.TileEntityAlloyFurnace;
import com.TRGamer28.coppermod.blocks.AlloyFurnace.slots.*;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAlloyFurnaceFuel extends Slot
{
	public SlotAlloyFurnaceFuel(IInventory inventory, int index, int x, int y) 
	{
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return TileEntityAlloyFurnace.isItemFuel(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		return super.getItemStackLimit(stack);
	}
}
