package com.khazoda.kreebles.entity;

import com.khazoda.kreebles.registry.MainRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class KreebleEntity extends PathfinderMob {
  private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(KreebleEntity.class, EntityDataSerializers.BYTE);
  public final AnimationState spawnAnimationState = new AnimationState();
  public final AnimationState restAnimationState = new AnimationState();
  public final AnimationState walkAnimationState = new AnimationState();
  int flag;

  public KreebleEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
    super(pEntityType, pLevel);
  }

  protected void registerGoals() {
    this.goalSelector.addGoal(1, new FloatGoal(this));
    this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.5f));
    this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
    this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
    this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
    this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(KreebleEntity.class));
    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, false));
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
    super.defineSynchedData(pBuilder);
    pBuilder.define(DATA_ID_FLAGS, (byte) 0);
  }

  public static AttributeSupplier.Builder registerAttributes() {
    return KreebleEntity.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 10.0D)
        .add(Attributes.MOVEMENT_SPEED, (double) 0.4D)
        .add(Attributes.KNOCKBACK_RESISTANCE, (double) 0.4D)
        .add(Attributes.FOLLOW_RANGE, (double) 30.0D)
        .add(Attributes.STEP_HEIGHT, 0.75D)
        .add(Attributes.SCALE, 2D)
        .add(Attributes.GRAVITY, 1.5D)
        .add(Attributes.ATTACK_DAMAGE, (double) 2.0D)
        .add(Attributes.ATTACK_SPEED, (double) 5.0D);
  }

  @Override
  public void tick() {
    super.tick();
    if (level().isClientSide) {
      this.setupAnimationStates();
    }
  }

  @Override
  public void aiStep() {
    LivingEntity target = this.getTarget();
    if (target != null && target instanceof ServerPlayer) {
      if (target.isHolding(MainRegistry.DASTARDLY_TALISMAN.get())) {
        if(flag == 0) {
          this.igniteForSeconds(1);
          level().explode(this, this.getX(), this.getY(), this.getZ(), 0f, Level.ExplosionInteraction.MOB);
          this.kill();
          flag = this.tickCount;
        }
      }
    }
    super.aiStep();
  }

  @Override
  public boolean hurt(DamageSource pSource, float pAmount) {
    if (this.isInvulnerableTo(pSource)) {
      return false;
    } else {
      return super.hurt(pSource, pAmount);
    }
  }

  private void setupAnimationStates() {
    if (isVectorLessThanThreshold(this.getDeltaMovement(), 0.01)) {
      this.walkAnimationState.stop();
      this.restAnimationState.startIfStopped(this.tickCount);
    } else {
      this.restAnimationState.stop();
      this.clientDiggingParticles(this.spawnAnimationState);
      this.spawnAnimationState.startIfStopped(this.tickCount);
      this.walkAnimationState.startIfStopped(this.tickCount);
    }
  }

  public static boolean isVectorLessThanThreshold(Vec3 vec, double threshold) {
    return vec.x() < threshold && vec.y() < threshold && vec.z() < threshold;
  }

  private void clientDiggingParticles(AnimationState pAnimationState) {
    if ((float) pAnimationState.getAccumulatedTime() < 400F) {
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
