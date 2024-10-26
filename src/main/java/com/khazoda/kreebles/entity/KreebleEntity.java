package com.khazoda.kreebles.entity;

import com.khazoda.kreebles.goal.KreebleAttackGoal;
import com.khazoda.kreebles.registry.MainRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class KreebleEntity extends PathfinderMob {
  private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(KreebleEntity.class, EntityDataSerializers.BYTE);
  public final AnimationState spawnAnimationState = new AnimationState();
  public final AnimationState restAnimationState = new AnimationState();
  public final AnimationState walkAnimationState = new AnimationState();
  public final AnimationState talismanFrozenAnimationState = new AnimationState();

  public KreebleEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
    super(pEntityType, pLevel);
  }

  protected void registerGoals() {
    this.goalSelector.addGoal(1, new FloatGoal(this));
    this.goalSelector.addGoal(2, new KreebleAttackGoal(this, 1.0D, false));
    this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
    this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8D));
    this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(KreebleEntity.class));
    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, false));
    this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Creeper.class, false));
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
        .add(Attributes.STEP_HEIGHT, 1.0D)
        .add(Attributes.SCALE, 2D)
        .add(Attributes.GRAVITY, 1.5D)
        .add(Attributes.ATTACK_DAMAGE, (double) 2.0D)
        .add(Attributes.ATTACK_SPEED, (double) 5.0D);
  }

  @Override
  public void tick() {
    super.tick();
    if (level().isClientSide) {
      this.runClientsideAnimations();
    }
  }

  @Override
  public void aiStep() {
    LivingEntity target = this.getTarget();
    if (target != null) {
      if (target.isHolding(MainRegistry.DASTARDLY_TALISMAN.get())) {
        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 100));
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

  private void runClientsideAnimations() {
    if (this.getDeltaMovement().equals(Vec3.ZERO)) {
      /** Maybe one day I'll get this to work */
//      this.restAnimationState.stop();
//      this.talismanFrozenAnimationState.startIfStopped(this.tickCount);
    } else if (isVectorLessThanThreshold(this.getDeltaMovement(), 0.0005)) {
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
    this.playSound(SoundEvents.AMETHYST_BLOCK_HIT, 3.0F, 1.0F);
    return super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, groupData);
  }


  @Nullable
  @Override
  protected SoundEvent getDeathSound() {
    return MainRegistry.KREEBLE_DEATH.get();
  }

  @Nullable
  @Override
  protected SoundEvent getHurtSound(DamageSource pDamageSource) {
    return MainRegistry.KREEBLE_HIT.get();
  }

  @Nullable
  @Override
  protected SoundEvent getAmbientSound() {
    return MainRegistry.KREEBLE_AMBIENT.get();
  }

  @Override
  protected void playAttackSound() {
    this.makeSound(MainRegistry.KREEBLE_ATTACK.get());
  }

  @Override
  protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
  }
}
