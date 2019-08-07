package com.TRGamer28.coppermod.enchantments;

import com.TRGamer28.coppermod.init.ModEnchantments;
import com.TRGamer28.coppermod.items.tools.ToolSai;
import com.TRGamer28.coppermod.util.Reference;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class EnchantmentBleeding extends Enchantment {


	private static final int damageType = 0;
	public EnchantmentBleeding() {
		super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        this.setName("bleeding");
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID + ": bleeding"));


        ModEnchantments.ENCHANTMENTS.add(this);
	}
	    
	public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return stack.getItem().canApplyAtEnchantingTable(stack, this);
    }
	  public int getMinEnchantability(int enchantmentLevel)
	    {
	        return 20 * enchantmentLevel;
	    }
	
	  public int getMaxEnchantability(int enchantmentLevel)
	    {
	        return this.getMinEnchantability(enchantmentLevel) + 20;
	    }

	  public boolean isTreasureEnchantment()
	    {
	        return true;
	    }
	  
	  public int getMaxLevel()
	    {
	        return 2;
	    }
	  public boolean canApply(ItemStack stack)
	    {
	        return stack.getItem() instanceof ItemAxe ? true : super.canApply(stack);

	    }
	  public boolean isAllowedOnBooks()
	    {
	        return true;
	    }
	 

	  public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
	    {
	        if (target instanceof EntityLivingBase)
	        {
	            EntityLivingBase entitylivingbase = (EntityLivingBase)target;
	            int i = 200 + user.getRNG().nextInt(10 * level);
                entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.POISON, i,3));
                entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, i,3));
                entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, i,3));
                entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.HUNGER, i,3));

	        }
	    }
	   


}
