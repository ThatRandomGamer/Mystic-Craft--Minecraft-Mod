package com.TRGamer28.coppermod.world.gen

public class WorldGenCustomStructures implements IWorldGenerator 
{

	public static final WorldGenStructure PYRAMID = new WorldGenStructure("pyramid");
	public static final WorldGenStructure TEMPLE = new WorldGenStructure("temple"");
	
	private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
		
		int x = (chunkX * 16) + random.nextInt(15);
		int z = (chunkZ * 16) + random.nextInt(15);
		int y = calculateGenerationHeight(world, x, z, topBlock);
		BlockPos pos = new BlockPos(x,y,z);
		
		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
		
		if(world.getWorldType() != WorldType.FLAT)
		{
			if(classesList.contains(biome))
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
				}
			}
		}
		
		
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
	
		switch(world.provider.getDimension())
		{
		case 1:
		
			break;
		
		case 0:
			
			generateStructure(TEMPLE, world, random, chunkX, chunkZ, 1, Blocks.GRASS, BiomeJungle.class);
			
			generateStructure(PYRAMID, world, random, chunkX, chunkZ, 1, Blocks.SAND, BiomeDesert.class);
			
			break;
			
		case -1:
		}
		
	}
	
	private static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
	{
		int y = world.getHeight();
		boolean foundGround = false;
		
		while(! foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			foundGround = block == topBlock;
		}
	
		return y;
	}
}
