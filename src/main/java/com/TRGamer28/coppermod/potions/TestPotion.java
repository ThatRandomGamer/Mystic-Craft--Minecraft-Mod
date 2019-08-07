package com.TRGamer28.coppermod.potions;

import com.TRGamer28.coppermod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class TestPotion extends Potion {

	public TestPotion(String name, boolean isBadPotion, int color, int iconIndexX, int iconIndexY ){

		super(isBadPotion, color);
		setPotionName("effect." + name);
		setIconIndex(iconIndexX, iconIndexY);
		setRegistryName(new ResourceLocation(Reference.MOD_ID +":" + name));
	}
	
	@Override
	public boolean hasStatusIcon() {
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID + "textrues/gui/rigid.png"));
        return true;		
	}
	
}
