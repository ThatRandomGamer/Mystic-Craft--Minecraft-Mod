package com.TRGamer28.coppermod.util.compat.JEI.Alloyer;

import java.awt.Color;
import java.util.List;

import com.TRGamer28.coppermod.blocks.AlloyFurnace.AlloyFurnaceRecipes;
import com.TRGamer28.coppermod.util.compat.JEI.JEICompat;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;

public class AlloyerRecipe implements IRecipeWrapper {

	private final List<ItemStack> inputs;
	private final ItemStack output;
	
	public AlloyerRecipe(List<ItemStack> inputs, ItemStack output) {
		
		this.inputs = inputs;
		this.output = output;
		}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		
		ingredients.setInput(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, output);
		
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
		AlloyFurnaceRecipes recipes = AlloyFurnaceRecipes.getInstance();
		float experience = recipes.getSinteringExperience(output);
		
		if(experience > 0) {
			
			String experienceString = JEICompat.translateToLocalFormatted("gui.jei.category.smelting.experience", experience);
			FontRenderer renderer = minecraft.fontRenderer;
			int stringWidth = renderer.getStringWidth(experienceString);
			renderer.drawString(experienceString, recipeWidth - stringWidth, 0, Color.GRAY.getRGB());
			
		}
		
		
	}
	
	
}
