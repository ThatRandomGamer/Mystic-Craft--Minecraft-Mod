package com.TRGamer28.coppermod.util.compat.JEI.Alloyer;

import com.TRGamer28.coppermod.util.Reference;
import com.TRGamer28.coppermod.util.compat.JEI.RecipeCategories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;

public class AlloyerRecipeCategory extends AbstractAlloyerRecipeCategory<AlloyerRecipe> {

	private final IDrawable background;
	private final String name;
	
	public AlloyerRecipeCategory(IGuiHelper helper) {
		
		super(helper);
        background = helper.createDrawable(TEXTRUES, 4, 12, 150, 60);
        name ="Alloy Furnace";
	
	}
	
	@Override
	public IDrawable getBackground() {
		
		return background;
		
	}
	
	@Override
	public void drawExtras (Minecraft minecraft) {
		
		animatedFlame.draw(minecraft, 4, 42);
		animatedArrow.draw(minecraft, 40, 23);
	}
	
     @Override
     public String getTitle() {
    	 
    	 return name;
     }
	
     @Override
     public String getModName() {
    	 
    	 return Reference.NAME;
     }
     
     @Override
     public String getUid() {
    	 
    	 return RecipeCategories.Alloyer;
     }
	public void setRecipe(IRecipeLayout recipeLayout, AlloyerRecipe recipeWrapper, IIngredients ingredients) {
		
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(input1, true, 21, 2);
		stacks.init(input2, true, 21, 42);
		stacks.init(output, false, 76, 23);
		
		stacks.set(ingredients);
	}
	
}
