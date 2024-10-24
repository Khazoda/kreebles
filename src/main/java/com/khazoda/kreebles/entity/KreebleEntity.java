package com.khazoda.kreebles.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class KreebleEntity extends PathfinderMob {
  private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(KreebleEntity.class, EntityDataSerializers.BYTE);
  private static final TargetingConditions BAT_RESTING_TARGETING = TargetingConditions.forNonCombat().range(4.0);
  public final AnimationState spawnAnimationState = new AnimationState();
  public final AnimationState restAnimationState = new AnimationState();
  @Nullable
  private BlockPos targetPosition;

  public KreebleEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
    super(pEntityType, pLevel);
    if (!pLevel.isClientSide) {
      this.setResting(true);
    }
  }

  protected void registerGoals() {
    this.goalSelector.addGoal(1, new FloatGoal(this));
    this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
    this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
    this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
    this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers(KreebleEntity.class));
  }

  @Override
  public boolean isFlapping() {
    return !this.isResting() && (float) this.tickCount % 10.0F == 0.0F;
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
    super.defineSynchedData(pBuilder);
    pBuilder.define(DATA_ID_FLAGS, (byte) 0);
  }

  public static AttributeSupplier.Builder registerAttributes() {
    return KreebleEntity.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 10.0D)
        .add(Attributes.MOVEMENT_SPEED, (double) 0.3D)
        .add(Attributes.KNOCKBACK_RESISTANCE, (double) 0.4D)
        .add(Attributes.FOLLOW_RANGE, (double) 30.0D)
        .add(Attributes.ATTACK_DAMAGE, (double) 2.0D)
        .add(Attributes.ATTACK_SPEED, (double) 5.0D);
  }

  public boolean isResting() {
    return (this.entityData.get(DATA_ID_FLAGS) & 1) != 0;
  }

  public void setResting(boolean pIsResting) {
    byte b0 = this.entityData.get(DATA_ID_FLAGS);
    if (pIsResting) {
      this.entityData.set(DATA_ID_FLAGS, (byte) (b0 | 1));
    } else {
      this.entityData.set(DATA_ID_FLAGS, (byte) (b0 & -2));
    }
  }

  @Override
  public void tick() {
    super.tick();
    if (this.isResting()) {
      this.setDeltaMovement(Vec3.ZERO);
    } else {
      this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
    }
    if (level().isClientSide) {
      this.setupAnimationStates();
    }
  }


  @Override
  public boolean hurt(DamageSource pSource, float pAmount) {
    if (this.isInvulnerableTo(pSource)) {
      return false;
    } else {
      if (!this.level().isClientSide && this.isResting()) {
        this.setResting(false);
      }
      return super.hurt(pSource, pAmount);
    }
  }

  @Override
  public void readAdditionalSaveData(CompoundTag pCompound) {
    super.readAdditionalSaveData(pCompound);
    this.entityData.set(DATA_ID_FLAGS, pCompound.getByte("BatFlags"));
  }

  @Override
  public void addAdditionalSaveData(CompoundTag pCompound) {
    super.addAdditionalSaveData(pCompound);
    pCompound.putByte("BatFlags", this.entityData.get(DATA_ID_FLAGS));
  }

  private void setupAnimationStates() {
    if (this.isResting()) {
      this.spawnAnimationState.stop();
      this.restAnimationState.startIfStopped(this.tickCount);
    } else {
      this.restAnimationState.stop();
      this.clientDiggingParticles(this.spawnAnimationState);
      this.spawnAnimationState.startIfStopped(this.tickCount);
    }
  }

  private void clientDiggingParticles(AnimationState pAnimationState) {
    if ((float) pAnimationState.getAccumulatedTime() < 1500F) {
      RandomSource randomsource = this.getRandom();
      BlockState blockstate = this.getBlockStateOn();
      if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
        for (int i = 0; i < 10; i++) {
          double d0 = this.getX() + (double) Mth.randomBetween(randomsource, -0.2F, 0.2F);
          double d1 = this.getY();
          double d2 = this.getZ() + (double) Mth.randomBetween(randomsource, -0.2F, 0.2F);
          this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0, 0.0, 0.0);
        }
      }
    }
  }

  @org.jetbrains.annotations.Nullable
  @Override
  public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance
      difficultyInstance, MobSpawnType spawnType, @org.jetbrains.annotations.Nullable SpawnGroupData groupData) {
    this.playSound(SoundEvents.AMETHYST_BLOCK_HIT, 5.0F, 1.0F);
    return super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, groupData);
  }

  @Override
  protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
  }

}
