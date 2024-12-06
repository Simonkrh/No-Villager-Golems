package com.simonkho.disableirongolemsspawns;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class DisableIronGolemsSpawns implements ModInitializer {
    @Override
    public void onInitialize() {
        GolemSpawnHandler.init();

        System.out.println("DisableIronGolemsSpawns: Mod initialized");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("golemcount")
                            .requires(source -> source.hasPermissionLevel(2)) // requires OP level 2 or higher
                            .executes(context -> {
                                var source = context.getSource();
                                var server = source.getServer();
                                ServerWorld world = server.getOverworld();
                                if (world != null) {
                                    GolemCountState state = world.getPersistentStateManager().getOrCreate(
                                            GolemCountState.TYPE,
                                            "golem_count"
                                    );
                                    int count = state.getGolemCount();
                                    source.sendMessage(Text.literal("Spawned golems prevented: " + count));
                                    return 1;
                                }
                                source.sendMessage(Text.literal("Could not find the world to get the golem count."));
                                return 0;
                            })

            );
        });
    }
}
