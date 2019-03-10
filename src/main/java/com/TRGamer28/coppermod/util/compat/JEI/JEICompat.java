package com.TRGamer28.coppermod.util.compat.JEI;

import java.util.IllegalFormatException;

import com.TRGamer28.coppermod.blocks.AlloyFurnace.ContainerAlloyFurnace;
import com.TRGamer28.coppermod.blocks.AlloyFurnace.GuiAlloyFurnace;
import com.TRGamer28.coppermod.util.compat.JEI.Alloyer.AlloyerRecipeCategory;
import com.TRGamer28.coppermod.util.compat.JEI.Alloyer.AlloyerRecipeMaker;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.util.text.translation.I18n;

public class JEICompat implements IModPlugin {

	@Override 
   public void registerCategories(IRecipeCategoryRegistration registry) {
		
		final IJeiHelpers helpers = registry.getJeiHelpers();
		final IGuiHelper gui = helpers.getGuiHelper();
		
		registry.addRecipeCategories(new AlloyerRecipeCategory(gui));
	}
	
	@Override
	public void register(IModRegistry registry) {
		
		final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry() ;
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();
		
		registry.addRecipes(AlloyerRecipeMaker.getRecipes(jeiHelpers), RecipeCategories.Alloyer);
		registry.addRecipeClickArea(GuiAlloyFurnace.class, 110, 0, 50, 50, RecipeCategories.Alloyer);
		recipeTransfer.addRecipeTransferHandler(ContainerAlloyFurnace.class, RecipeCategories.Alloyer, 0, 1, 3, 36);


	}
	
	public static String translateToLocal(String key) {
		if (I18n.canTranslate(key)) return I18n.translateToLocal(key);
				else return I18n.translateToFallback(key);
	}
     public static String translateToLocalFormatted(String key, Object... format){

    	String s = translateToLocal(key);
    	try {
    		return String.format(s, format);
    	}
    	catch(IllegalFormatException e) {
    		return "Format error: " + s;
    	}
    	 
     } 

}


