package com.TRGamer28.coppermod.util.compat.JEI.Alloyer;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.TRGamer28.coppermod.blocks.AlloyFurnace.AlloyFurnaceRecipes;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public class AlloyerRecipeMaker {
	 
	public static List<AlloyerRecipe> getRecipes(IJeiHelpers helpers){
		
		IStackHelper stackHelper = helpers.getStackHelper();
		AlloyFurnaceRecipes instance = AlloyFurnaceRecipes.getInstance();
		Table<ItemStack, ItemStack, ItemStack> recipes = instance.getDualSmeltingList();
		List<AlloyerRecipe> jeiRecipes = Lists.newArrayList();
		
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : recipes.columnMap().entrySet()) {
			
			for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
				
				ItemStack input1 = entry.getKey();
				ItemStack input2 = ent.getKey();
				ItemStack output = ent.getValue();
				List<ItemStack> inputs = Lists.newArrayList(input1, input2);
				AlloyerRecipe recipe = new AlloyerRecipe(inputs, output);
				jeiRecipes.add(recipe);
				
			}
		}
		
		return jeiRecipes;
	}
	
	
	
		
}
