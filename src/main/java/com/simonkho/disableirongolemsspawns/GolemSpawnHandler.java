package com.simonkho.disableirongolemsspawns;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.server.world.ServerWorld;

public class GolemSpawnHandler {
    public static void init() {
        ServerEntityEvents.ENTITY_LOAD.register((Entity entity, ServerWorld world) -> {
            if (entity.getType() == EntityType.IRON_GOLEM && entity instanceof IronGolemEntity golem) {
                if (!golem.isPlayerCreated()) {
                    // Prevent the golem spawn
                    entity.remove(Entity.RemovalReason.DISCARDED);

                    GolemCountState state = world.getPersistentStateManager().getOrCreate(
                            GolemCountState.TYPE,
                            "golem_count"
                    );
                    state.increment();
                }
            }
        });
    }
}
