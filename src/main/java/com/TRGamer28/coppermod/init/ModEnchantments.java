package com.TRGamer28.coppermod.init;

import java.util.ArrayList;
import net.minecraftforge.common.util.EnumHelper;
import java.util.List;

import com.TRGamer28.coppermod.enchantments.EnchantmentBleeding;
import com.TRGamer28.coppermod.enchantments.EnchantmentHeatWalker;
import com.TRGamer28.coppermod.enchantments.EnchantmentIceAspect;
import com.TRGamer28.coppermod.enchantments.EnchantmentThunder;
import com.TRGamer28.coppermod.items.tools.ToolBow;
import com.TRGamer28.coppermod.items.tools.ToolMace;
import com.TRGamer28.coppermod.items.tools.ToolSai;
import com.TRGamer28.coppermod.util.Reference;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModEnchantments {

	public static final List<Enchantment> ENCHANTMENTS = new ArrayList<Enchantment>();
    public static final EnumEnchantmentType WEAPONS = EnumHelper.addEnchantmentType("weapons", (item)->(item instanceof ItemSword || item instanceof ToolSai || item instanceof ToolMace));
    public static final EnumEnchantmentType BOW = EnumHelper.addEnchantmentType("weapons", (item)->(item instanceof ItemBow || item instanceof ToolBow));

	//Enchantments
	
	public static final Enchantment ICE_ASPECT = new EnchantmentIceAspect();
	public static final Enchantment THUNDER = new EnchantmentThunder();
	public static final Enchantment HEAT_WALKER = new EnchantmentHeatWalker();
	public static final Enchantment BLEEDING = new EnchantmentBleeding();
/*public static void createMagmaNearby(LivingUpdateEvent event) 
	{	
		EntityLivingBase living = event.getEntityLiving();
		int level = EnchantmentHelper.getMaxEnchantmentLevel(HEAT_WALKER, living);
		BlockPos pos = living.getPosition();
		
		if(level > 0)
		{
			World world = event.getEntity().world;
			if (living.onGround)
			{
				float f = (float)Math.min(16, 2 + level);
				BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos(0, 0, 0);

				for (BlockPos.MutableBlockPos blockpos1 : BlockPos.getAllInBoxMutable(pos.add((double)(-f), -1.0D, (double)(-f)), pos.add((double)f, -1.0D, (double)f)))
				{
					if (blockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double)(f * f))
					{
						blockpos.setPos(blockpos1.getX(), blockpos1.getY() + 1, blockpos1.getZ());
						IBlockState iblockstate = world.getBlockState(blockpos);

						if (iblockstate.getMaterial() == Material.AIR)
						{
							IBlockState iblockstate1 = world.getBlockState(blockpos1);

							if (iblockstate1.getMaterial() == Material.LAVA && (iblockstate1.getBlock() == Blocks.LAVA || iblockstate1.getBlock() == Blocks.FLOWING_LAVA) && ((Integer)iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && world.mayPlace(Blocks.MAGMA, blockpos1, false, EnumFacing.DOWN, (Entity)null))
							{
								world.setBlockState(blockpos1, Blocks.MAGMA.getDefaultState());
								world.scheduleUpdate(blockpos1.toImmutable(), Blocks.MAGMA, MathHelper.getInt(living.getRNG(), 60, 120));
								living.addPotionEffect(new PotionEffect(Potion.getPotionById(12)));
							}
						}
					}
				}
			}
		}
	}
	*/

	@SubscribeEvent
	 public static void HeatNearby(LivingUpdateEvent event)
    {
		{	
			EntityLivingBase living = event.getEntityLiving();
			int level = EnchantmentHelper.getMaxEnchantmentLevel(HEAT_WALKER, living);
			BlockPos pos = living.getPosition();
			 if (living.onGround)
				 if(level > 0)
					{
						World worldIn = event.getEntity().world;
						if (living.onGround)
        {
            float f = (float)Math.min(16, 2 + level);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

            for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add((double)(-f), -1.0D, (double)(-f)), pos.add((double)f, -1.0D, (double)f)))
            {
                if (blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double)(f * f))
                {
                    blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                    IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);

                    if (iblockstate.getMaterial() == Material.AIR)
                    {
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);

                        if (iblockstate1.getMaterial() == Material.LAVA && (iblockstate1.getBlock() == net.minecraft.init.Blocks.LAVA || iblockstate1.getBlock() == net.minecraft.init.Blocks.FLOWING_LAVA) && ((Integer)iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && worldIn.mayPlace(Blocks.MAGMA, blockpos$mutableblockpos1, false, EnumFacing.DOWN, (Entity)null))
                        {
                            worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.MAGMA.getDefaultState());
                            worldIn.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), Blocks.MAGMA, MathHelper.getInt(living.getRNG(), 60, 120));
                        }
                    }
                }
            }
        }
    }
		}
		}
}
