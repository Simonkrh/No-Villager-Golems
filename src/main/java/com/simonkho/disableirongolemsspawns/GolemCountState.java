package com.simonkho.disableirongolemsspawns;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.world.PersistentState;
import net.minecraft.datafixer.DataFixTypes;

public class GolemCountState extends PersistentState {
    private int golemCount = 0;

    public static final PersistentState.Type<GolemCountState> TYPE = new PersistentState.Type<>(
            GolemCountState::create,
            GolemCountState::load,
            DataFixTypes.LEVEL
    );

    public static GolemCountState create() {
        return new GolemCountState();
    }

    public static GolemCountState load(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        GolemCountState state = new GolemCountState();
        state.golemCount = nbt.getInt("GolemCount");
        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, WrapperLookup lookup) {
        nbt.putInt("GolemCount", golemCount);
        return nbt;
    }

    public int getGolemCount() {
        return golemCount;
    }

    public void increment() {
        golemCount++;
        markDirty();
    }
}
