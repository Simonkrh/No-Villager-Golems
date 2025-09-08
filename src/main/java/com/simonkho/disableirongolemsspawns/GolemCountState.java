package com.simonkho.disableirongolemsspawns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.datafixer.DataFixTypes;

public class GolemCountState extends PersistentState {
    private int golemCount;

    // --- Codec for serializing/deserializing this state ---
    public static final Codec<GolemCountState> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("GolemCount").forGetter(GolemCountState::getGolemCount))
            .apply(inst, GolemCountState::newFromInt));

    // --- PersistentStateType holds id + constructor + codec + fix type ---
    public static final PersistentStateType<GolemCountState> TYPE = new PersistentStateType<>("golem_count",
            GolemCountState::new, CODEC, DataFixTypes.LEVEL);

    // Constructors used by supplier/codec
    private GolemCountState() {
        this.golemCount = 0;
    }

    private static GolemCountState newFromInt(int count) {
        GolemCountState s = new GolemCountState();
        s.golemCount = count;
        return s;
    }

    public int getGolemCount() {
        return golemCount;
    }

    public void increment() {
        this.golemCount++;
        this.markDirty();
    }

    // Helper to fetch/create from a world
    public static GolemCountState get(ServerWorld world) {
        PersistentStateManager mgr = world.getPersistentStateManager();
        return mgr.getOrCreate(TYPE); // 1-arg overload on 1.21.6
    }
}
