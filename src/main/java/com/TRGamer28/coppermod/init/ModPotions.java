package com.TRGamer28.coppermod.init;

import java.util.ArrayList;
import java.util.List;

import com.TRGamer28.coppermod.potions.TestPotion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModPotions {

// use https://www.mathsisfun.com/hexadecimal-decimal-colors.html for colors
	public static final Potion RIGIDITY = new TestPotion("test_potion", false, 6435487,0,0).registerPotionAttributeModifier(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, MathHelper.getRandomUUID().toString(), 6.00, 2);
	
	public static final PotionType RIGIDITY_POTION = new PotionType("test_potion",new PotionEffect[] {(new PotionEffect(RIGIDITY,2400))}).setRegistryName("test_potion");
	public static final PotionType LONG_RIGIDITY_POTION = new PotionType("test_potion",new PotionEffect[] {(new PotionEffect(RIGIDITY,4800))}).setRegistryName("long_test_potion");

	public static void registerPotions() {
		registerPotion(LONG_RIGIDITY_POTION ,RIGIDITY_POTION, RIGIDITY);
	    registerPotionMixes();
	}
	private static void registerPotion(PotionType defaultPotion, PotionType longPotion, Potion effect) {
		
		ForgeRegistries.POTIONS.register(effect);
		ForgeRegistries.POTION_TYPES.register(defaultPotion);
		ForgeRegistries.POTION_TYPES.register(longPotion);

	}
	
	private static void registerPotionMixes() {
		
		PotionHelper.addMix( RIGIDITY_POTION, Items.REDSTONE, LONG_RIGIDITY_POTION);
		PotionHelper.addMix( PotionTypes.AWKWARD, ModItems.CARBON_CRYSTAL, RIGIDITY_POTION);

	}
}

