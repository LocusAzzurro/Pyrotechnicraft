package org.mineplugin.locusazzurro.pyrotechnicraft.world.data;

import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.particle.FireworkSparkParticleOption;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.FireworkShapeProcessor;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.IExplosionShape;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.SparkPointsVisitor;

import java.util.List;

public final class FireworkEngine {

    public static void createFirework(Level level, Vec3 pos, Vec3 mov, CompoundTag data){
        boolean custom = data.getBoolean("IsCustom");
        CompoundTag payload = data.getCompound("Payload");
        if (custom){
            explodeFireworkOrb(level, pos, mov, payload);
        }
        else explodeFireworkStar(level, pos, mov, payload);
    }

    static void explodeFireworkOrb(Level level, Vec3 pos, Vec3 mov, CompoundTag tag){
        IExplosionShape exp = FireworkShapeProcessor.processShapeTag(tag, mov.normalize());
        boolean trail = tag.contains("Trail") && tag.getBoolean("Trail");
        boolean sparkle = tag.contains("Sparkle") && tag.getBoolean("Sparkle");
        int[] colors = tag.contains("Colors") ? tag.getIntArray("Colors") : new int[]{0xffffff};
        int[] fadeColors = tag.contains("FadeColors") ? tag.getIntArray("FadeColors") : new int[0];
        boolean hasFade = fadeColors.length > 0;
        SparkPointsVisitor visitor = new SparkPointsVisitor(level.random);
        List<Vec3> points = exp.accept(visitor);
        points.forEach((p) ->{
            int color = colors[level.random.nextInt(colors.length)];
            int fadeColor = hasFade ? fadeColors[level.random.nextInt(fadeColors.length)] : color;
            level.addParticle(new FireworkSparkParticleOption(color, fadeColor, trail, sparkle),
                    pos.x(), pos.y(), pos.z(), p.x(), p.y(), p.z());
        });

    }

    static void explodeFireworkStar(Level level, Vec3 pos, Vec3 mov, CompoundTag payload){
        level.createFireworks(pos.x(), pos.y(), pos.z(), mov.x(), mov.y(), mov.z(),
                FireworkWrapper.wrapSingletonFireworkStar(payload));
    }

}
