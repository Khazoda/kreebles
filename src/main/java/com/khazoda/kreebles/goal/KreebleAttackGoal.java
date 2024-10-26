package com.khazoda.kreebles.goal;

import com.khazoda.kreebles.entity.KreebleEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Zombie;

/* Mimics behaviour from ZombieAttackGoal in order to do attack animations */
public class KreebleAttackGoal extends MeleeAttackGoal {
  private final KreebleEntity kreeble;
  private int raiseArmTicks;

  public KreebleAttackGoal(KreebleEntity pKreeble, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
    super(pKreeble, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
    this.kreeble =  pKreeble;
  }

  @Override
  public void start() {
    super.start();
    this.raiseArmTicks = 0;
  }

  @Override
  public void stop() {
    super.stop();
    this.kreeble.setAggressive(false);
  }

  @Override
  public void tick() {
    super.tick();
    this.raiseArmTicks++;
    if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
      this.kreeble.setAggressive(true);
    } else {
      this.kreeble.setAggressive(false);
    }
  }
}
