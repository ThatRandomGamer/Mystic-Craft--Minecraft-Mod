package com.TRGamer28.coppermod.proxy;


import net.minecraft.util.ResourceLocation;

import com.TRGamer28.coppermod.util.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy 
{
	@Override
	public void registerItemRenderer(Item item, int meta, String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void registerVariantRenderer(Item item, int meta, String filename, String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, filename), id));
	}
	
	private void setupTile(Class<? extends TileEntity> class1, TileEntitySpecialRenderer render) {
		try {
			ClientRegistry.bindTileEntitySpecialRenderer(class1, render.getClass().newInstance());
		} catch (Exception e) {
			e.printStackTrace();
			}
			GameRegistry.registerTileEntity(class1, class1.getName());
	}
}