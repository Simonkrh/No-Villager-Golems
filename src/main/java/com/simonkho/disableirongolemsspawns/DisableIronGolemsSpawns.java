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

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("golemcount")
                            .requires(source -> source.hasPermissionLevel(2))
                            .executes(context -> {
                                var source = context.getSource();
                                ServerWorld world = source.getServer().getOverworld();
                                if (world != null) {
                                    int count = GolemCountState.get(world).getGolemCount();
                                    source.sendMessage(Text.literal("Spawned golems prevented: " + count));
                                    return 1;
                                }
                                source.sendMessage(Text.literal("Could not find the world to get the golem count."));
                                return 0;
                            }));
        });
    }
}
