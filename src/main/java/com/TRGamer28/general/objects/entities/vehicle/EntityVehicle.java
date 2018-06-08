package com.TRGamer28.general.objects.entities.vehicle;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import com.TRGamer28.coppermod.init.CopperModItems;
import com.TRGamer28.general.Main;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public abstract class EntityVehicle extends Entity
{
    private static final DataParameter<Float> CURRENT_SPEED = EntityDataManager.createKey(EntityVehicle.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> MAX_SPEED = EntityDataManager.createKey(EntityVehicle.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> ACCELERATION_SPEED = EntityDataManager.createKey(EntityVehicle.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> TURN_DIRECTION = EntityDataManager.createKey(EntityVehicle.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TURN_SENSITIVITY = EntityDataManager.createKey(EntityVehicle.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> MAX_TURN_ANGLE = EntityDataManager.createKey(EntityVehicle.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ACCELERATION_DIRECTION = EntityDataManager.createKey(EntityVehicle.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityVehicle.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(EntityVehicle.class, DataSerializers.FLOAT);
    

    public float prevCurrentSpeed;
    public float currentSpeed;
    public float speedMultiplier;

    public int turnAngle;
    public int prevTurnAngle;

    public float deltaYaw;
    public float wheelAngle;
    public float prevWheelAngle;

    public float vehicleMotionX;
    public float vehicleMotionY;
    public float vehicleMotionZ;

    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;

    protected State state;
    protected State previousState;

    private Vec3d heldOffset = Vec3d.ZERO;


    protected EntityVehicle(World worldIn)
    {
        super(worldIn);
        this.setSize(1F, 1F);
        this.stepHeight = 1.0F;
    }

    public EntityVehicle(World worldIn, double posX, double posY, double posZ)
    {
        this(worldIn);
        this.setPosition(posX, posY, posZ);
    }

    public float getMinEnginePitch()
    {
        return 0.5F;
    }

    public float getMaxEnginePitch()
    {
        return 1.2F;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    public void entityInit()
    {
        this.dataManager.register(CURRENT_SPEED, 0F);
        this.dataManager.register(MAX_SPEED, 10F);
        this.dataManager.register(ACCELERATION_SPEED, 0.5F);
        this.dataManager.register(TURN_DIRECTION, TurnDirection.FORWARD.ordinal());
        this.dataManager.register(TURN_SENSITIVITY, 10);
        this.dataManager.register(MAX_TURN_ANGLE, 45);
        this.dataManager.register(ACCELERATION_DIRECTION, AccelerationDirection.NONE.ordinal());
        this.dataManager.register(TIME_SINCE_HIT, 0);
        this.dataManager.register(DAMAGE_TAKEN, 0F);

        if(this.world.isRemote)
        {
            
            onClientInit();
        }
    }

    @SideOnly(Side.CLIENT)
    public void onClientInit() {}

    @Override
    public void onUpdate()
    {
        if(this.getTimeSinceHit() > 0)
        {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if(this.getDamageTaken() > 0.0F)
        {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        super.onUpdate();
        this.tickLerp();

        prevCurrentSpeed = currentSpeed;
        prevTurnAngle = turnAngle;
        prevWheelAngle = wheelAngle;

       

        /* If there driver, create particles */
       

        /* Handle the current speed of the vehicle based on rider's forward movement */
        this.updateSpeed();
        this.updateTurning();
        this.updateVehicle();
        this.setSpeed(currentSpeed);

        /* Updates the direction of the vehicle */
        rotationYaw -= deltaYaw;

        /* Updates the vehicle motion and applies it on top of the normal motion */
        this.updateVehicleMotion();
        move(MoverType.SELF, motionX + vehicleMotionX, motionY + vehicleMotionY, motionZ + vehicleMotionZ);

        /* Reduces the motion and speed multiplier */
        motionX *= 0.8;
        motionY *= 0.98D;
        motionZ *= 0.8;
        speedMultiplier *= 0.85;

        /* Checks for block collisions */
        this.doBlockCollisions();

      
      /* Checks for collisions with any other vehicles */
        /**  
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox(), entity -> entity instanceof EntityBumperCar);
        if (!list.isEmpty())
        {
            for(Entity entity : list)
            {
                this.applyEntityCollision(entity);
            }
        } **/
    }

    public void updateVehicle() {}

    public abstract void updateVehicleMotion();

    /**
     * Smooths the rendering on servers
     */
    private void tickLerp()
    {
        if(this.lerpSteps > 0 && !this.canPassengerSteer())
        {
            double d0 = this.posX + (this.lerpX - this.posX) / (double) this.lerpSteps;
            double d1 = this.posY + (this.lerpY - this.posY) / (double) this.lerpSteps;
            double d2 = this.posZ + (this.lerpZ - this.posZ) / (double) this.lerpSteps;
            double d3 = MathHelper.wrapDegrees(this.lerpYaw - (double) this.rotationYaw);
            this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
            this.rotationPitch = (float) ((double) this.rotationPitch + (this.lerpPitch - (double) this.rotationPitch) / (double) this.lerpSteps);
            --this.lerpSteps;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }

    private void updateSpeed()
    {
        currentSpeed = this.getSpeed();
        AccelerationDirection acceleration = this.getAcceleration();
        if(this.getControllingPassenger() != null)
        {
            if(acceleration == AccelerationDirection.FORWARD)
            {
                this.currentSpeed += this.getAccelerationSpeed(); //
                if(this.currentSpeed > this.getMaxSpeed() ) //
                {
                    this.currentSpeed = this.getMaxSpeed(); //makes speed into max speed if max speed is exceeded
                }
            }
            else if(acceleration == AccelerationDirection.REVERSE)
            {
                this.currentSpeed -= this.getAccelerationSpeed(); //* acceleration multiplier factor
                if(this.currentSpeed < -(4.0F  / 2))
                {
                    this.currentSpeed = -(4.0F / 2);
                }
            }
            else
            {
                this.currentSpeed *= 0.9;
            }
        }
        else
        {
            this.currentSpeed *= 0.5;
        }

        /* Applies the speed multiplier to the current speed */
        currentSpeed = currentSpeed + (currentSpeed * speedMultiplier);
    }

    private void updateTurning()
    {
        TurnDirection direction = this.getTurnDirection();
        if(this.getControllingPassenger() != null && direction != TurnDirection.FORWARD)
        {
            this.turnAngle += direction.dir * getTurnSensitivity();
            if(Math.abs(this.turnAngle) > getMaxTurnAngle())
            {
                this.turnAngle = getMaxTurnAngle() * direction.dir;
            }
        }
        else
        {
            this.turnAngle *= 0.75;
        }
        this.wheelAngle = this.turnAngle * Math.max(0.25F, 1.0F - Math.abs(currentSpeed / 30F));
        this.deltaYaw = this.wheelAngle * (currentSpeed / 30F) / 2F;
    }


/**
    @SideOnly(Side.CLIENT)
    public void onClientUpdate()
    {
        EntityLivingBase entity = (EntityLivingBase) this.getControllingPassenger();
        if(entity != null && entity.equals(Minecraft.getMinecraft().player))
        {
        	
            AccelerationDirection acceleration = AccelerationDirection.fromEntity(entity);
            if(this.getAcceleration() != acceleration)
            {
                this.setAcceleration(acceleration);
                PacketHandler.INSTANCE.sendToServer(new MessageAccelerating(acceleration));
            }

            

            TurnDirection direction = TurnDirection.FORWARD;
            if(entity.moveStrafing < 0)
            {
                direction = TurnDirection.RIGHT;
            }
            else if(entity.moveStrafing > 0)
            {
                direction = TurnDirection.LEFT;
            }
            if(this.getTurnDirection() != direction)
            {
                this.setTurnDirection(direction);
                PacketHandler.INSTANCE.sendToServer(new MessageTurn(direction));
            } 
        } 
    } **/

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if(!world.isRemote && !player.isSneaking())
        {
            player.startRiding(this);
        }
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if(!this.world.isRemote && !this.isDead)
        {
            if(source instanceof EntityDamageSourceIndirect && source.getTrueSource() != null && this.isPassenger(source.getTrueSource()))
            {
                return false;
            }
            else
            {
                this.setTimeSinceHit(10);
                this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);

                boolean flag = source.getTrueSource() instanceof EntityPlayer && ((EntityPlayer) source.getTrueSource()).capabilities.isCreativeMode;
                if(flag || this.getDamageTaken() > 40.0F)
                {
                    if(!flag && this.world.getGameRules().getBoolean("doEntityDrops"))
                    {
                        //this.dropItemWithOffset(this.getItemBoat(), 1, 0.0F);
                    }

                    this.setDead();
                }

                return true;
            }
        }
        else
        {
            return true;
        }
    }

    @Override
    public abstract double getMountedYOffset();

    @Override
    protected boolean canBeRidden(Entity entityIn)
    {
        return true;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
      
        if(compound.hasKey("maxSpeed", Constants.NBT.TAG_FLOAT))
        {
            this.setMaxSpeed(compound.getFloat("maxSpeed"));
        }
        if(compound.hasKey("accelerationSpeed", Constants.NBT.TAG_FLOAT))
        {
            this.setAccelerationSpeed(compound.getFloat("accelerationSpeed"));
        }
        if(compound.hasKey("turnSensitivity", Constants.NBT.TAG_INT))
        {
            this.setTurnSensitivity(compound.getInteger("turnSensitivity"));
        }
        if(compound.hasKey("maxTurnAngle", Constants.NBT.TAG_INT))
        {
            this.setMaxTurnAngle(compound.getInteger("maxTurnAngle"));
        }
        if(compound.hasKey("stepHeight", Constants.NBT.TAG_FLOAT))
        {
            this.stepHeight = compound.getFloat("stepHeight");
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        
        compound.setFloat("maxSpeed", this.getMaxSpeed());
        compound.setFloat("accelerationSpeed", this.getAccelerationSpeed());
        compound.setInteger("turnSensitivity", this.getTurnSensitivity());
        compound.setInteger("maxTurnAngle", this.getMaxTurnAngle());
        compound.setFloat("stepHeight", this.stepHeight);
    }

    @Nullable
    public Entity getControllingPassenger()
    {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public void updatePassenger(Entity passenger)
    {
        super.updatePassenger(passenger);
        //TODO change to config option
        passenger.rotationYaw -= deltaYaw;
        passenger.setRotationYawHead(passenger.rotationYaw);
        applyYawToEntity(passenger);
    }

    protected void applyYawToEntity(Entity entityToUpdate)
    {
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -120.0F, 120.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate)
    {
        this.applyYawToEntity(entityToUpdate);
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {}

    @SideOnly(Side.CLIENT)
    public void performHurtAnimation()
    {
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }

    public boolean isMoving()
    {
        return this.currentSpeed != 0;
    }

    public void setMaxSpeed(float maxSpeed)
    {
        this.dataManager.set(MAX_SPEED, maxSpeed);
    }

    public float getMaxSpeed()
    {
        return this.dataManager.get(MAX_SPEED);
    }

    public void setSpeed(float speed)
    {
        this.dataManager.set(CURRENT_SPEED, speed);
    }

    public float getSpeed()
    {
        return this.currentSpeed;
    }

    public float getNormalSpeed()
    {
        return this.currentSpeed / this.getMaxSpeed();
    }

    public float getAccelerationSpeed()
    {
        return this.dataManager.get(ACCELERATION_SPEED);
    }

    public void setAccelerationSpeed(float speed)
    {
        this.dataManager.set(ACCELERATION_SPEED, speed);
    }

    public double getKilometersPreHour()
    {
        return Math.sqrt(Math.pow(this.posX - this.prevPosX, 2) + Math.pow(this.posZ - this.prevPosZ, 2)) * 20;
    }

    public void setTurnDirection(TurnDirection turnDirection)
    {
        this.dataManager.set(TURN_DIRECTION, turnDirection.ordinal());
    }

    public TurnDirection getTurnDirection()
    {
        return TurnDirection.values()[this.dataManager.get(TURN_DIRECTION)];
    }

    public void setAcceleration(AccelerationDirection direction)
    {
        this.dataManager.set(ACCELERATION_DIRECTION, direction.ordinal());
    }

    public AccelerationDirection getAcceleration()
    {
        return AccelerationDirection.values()[this.dataManager.get(ACCELERATION_DIRECTION)];
    }

    public void setTurnSensitivity(int sensitivity)
    {
        this.dataManager.set(TURN_SENSITIVITY, sensitivity);
    }

    public int getTurnSensitivity()
    {
        return this.dataManager.get(TURN_SENSITIVITY);
    }

    public void setMaxTurnAngle(int turnAngle)
    {
        this.dataManager.set(MAX_TURN_ANGLE, turnAngle);
    }

    public int getMaxTurnAngle()
    {
        return this.dataManager.get(MAX_TURN_ANGLE);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit)
    {
        this.dataManager.set(TIME_SINCE_HIT, timeSinceHit);
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit()
    {
        return this.dataManager.get(TIME_SINCE_HIT);
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float damageTaken)
    {
        this.dataManager.set(DAMAGE_TAKEN, damageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken()
    {
        return this.dataManager.get(DAMAGE_TAKEN);
    }


    public Vec3d getHeldOffset()
    {
        return heldOffset;
    }

 

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYaw = (double) yaw;
        this.lerpPitch = (double) pitch;
        this.lerpSteps = 10;
    }

    @Override
    protected void addPassenger(Entity passenger)
    {
        super.addPassenger(passenger);
        if(this.canPassengerSteer() && this.lerpSteps > 0)
        {
            this.lerpSteps = 0;
            this.posX = this.lerpX;
            this.posY = this.lerpY;
            this.posZ = this.lerpZ;
            this.rotationYaw = (float) this.lerpYaw;
            this.rotationPitch = (float) this.lerpPitch;
        }
    }

    public enum TurnDirection
    {
        LEFT(1), FORWARD(0), RIGHT(-1);

        final int dir;

        TurnDirection(int dir)
        {
            this.dir = dir;
        }

        public int getDir()
        {
            return dir;
        }
    }

    public enum AccelerationDirection
    {
        FORWARD, NONE, REVERSE;

        public static AccelerationDirection fromEntity(EntityLivingBase entity)
        {
            if(entity.moveForward > 0)
            {
                return FORWARD;
            }
            else if(entity.moveForward < 0)
            {
                return REVERSE;
            }
            return NONE;
        }
    }

    public State getDefaultState() {
        return State.ON_LAND;
    }

    protected enum State
    {
        ON_WATER, UNDER_WATER, IN_AIR, ON_LAND
    }
}