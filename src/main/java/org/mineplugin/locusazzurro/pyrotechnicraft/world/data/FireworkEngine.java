package org.mineplugin.locusazzurro.pyrotechnicraft.world.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireworkEngine {

    public static void createFirework(Level level, Vec3 pos, Vec3 mov, CompoundTag data){
        boolean custom = data.getBoolean("IsCustom");
        CompoundTag payload = data.getCompound("Payload");
        if (custom){

        }
        else explodeFireworkStar(level, pos, mov, payload);
    }

    public static void explodeFireworkStar(Level level, Vec3 pos, Vec3 mov, CompoundTag payload){
        level.createFireworks(pos.x(), pos.y(), pos.z(), mov.x(), mov.y(), mov.z(),
                VanillaFireworkWrapper.wrapSingletonFireworkStar(payload));
    }

}
