package org.mineplugin.locusazzurro.pyrotechnicraft.world.item;

import net.minecraft.nbt.CompoundTag;

public abstract class FireworkPayloadData {

    public abstract CompoundTag serialize();
    public abstract boolean isVanillaFormat();

    }

