package com.simonkho.disableirongolemsspawns;

import net.fabricmc.api.ModInitializer;

public class DisableIronGolemsSpawns implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("Disable Iron Golems Mod Loaded!");
        GolemSpawnHandler.init();
    }
}
