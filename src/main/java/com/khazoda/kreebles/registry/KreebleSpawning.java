package com.khazoda.kreebles.registry;

import com.khazoda.kreebles.entity.KreebleEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import static com.khazoda.kreebles.registry.MainRegistry.KREEBLE;

public class KreebleSpawning {

  public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
    event.register(KREEBLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
  }

  public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
    event.put(KREEBLE.get(), KreebleEntity.registerAttributes().build());
  }
}
