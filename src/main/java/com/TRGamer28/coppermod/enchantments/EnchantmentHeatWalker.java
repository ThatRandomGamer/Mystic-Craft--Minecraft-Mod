package com.TRGamer28.coppermod.enchantments;

import com.TRGamer28.coppermod.init.ModEnchantments;
import com.TRGamer28.coppermod.util.Reference;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EnchantmentHeatWalker extends Enchantment {


	public EnchantmentHeatWalker() {
		super(Rarity.RARE, EnumEnchantmentType.ARMOR_FEET, new EntityEquipmentSlot[] {EntityEquipmentSlot.FEET});
        this.setName("heat_walker");
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID + ":heat_walker"));
	
        ModEnchantments.ENCHANTMENTS.add(this);
	}
	  public int getMinEnchantability(int enchantmentLevel)
	    {
	        return 20 * enchantmentLevel;
	    }
	
	  public int getMaxEnchantability(int enchantmentLevel)
	    {
	        return this.getMinEnchantability(enchantmentLevel) + 10;
	    }

	  public int getMaxLevel()
	    {
	        return 4;
	    }
	 
	  public boolean isTreasureEnchantment()
	    {
	        return true;
	    }
	  
	  
	  public boolean isAllowedOnBooks()
	    {
	        return true;
	    }
	  public boolean canApplyAtEnchantingTable(ItemStack stack)
	    {
	        return stack.getItem().canApplyAtEnchantingTable(stack, this);
	    }
	  
	  public boolean canApplyTogether(Enchantment ench)
	    {
	        return super.canApplyTogether(ench) && ench != Enchantments.FROST_WALKER&& ench != Enchantments.DEPTH_STRIDER;
	    }
	
}
