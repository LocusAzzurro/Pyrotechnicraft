package org.mineplugin.locusazzurro.pyrotechnicraft.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.util.ColorUtil;

public class TrailSparkParticle extends TextureSheetParticle {

    public TrailSparkParticle(ClientLevel level, Vec3 pos, int color) {
        super(level, pos.x, pos.y, pos.z, 0, 0, 0);
        this.xd = (level.random.nextDouble() - 0.5) * 0.5;
        this.yd = (level.random.nextDouble() - 0.5) * 0.5;
        this.zd = (level.random.nextDouble() - 0.5) * 0.5;
        this.scale(level.random.nextFloat()/0.5f);
        this.setColor(ColorUtil.redF(color), ColorUtil.greenF(color), ColorUtil.blueF(color));
        this.lifetime = 20 + level.random.nextInt(5);
    }

    @Override
    public void tick(){
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) this.remove();
        else {
            this.move(xd, yd, zd);
            this.xd *= 0.9d;
            this.yd *= 0.9d;
            this.zd *= 0.9d;
        }
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public int getLightColor(float pPartialTick) {
        return 15728880;
    }

    @OnlyIn(Dist.CLIENT)
    public static class TrailSparkProvider implements ParticleProvider<TrailSparkParticleOption> {

        private final SpriteSet sprites;

        public TrailSparkProvider(SpriteSet sprite) {
            this.sprites = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(TrailSparkParticleOption option, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            TrailSparkParticle particle = new TrailSparkParticle(pLevel, new Vec3(pX, pY, pZ), option.color());
            particle.pickSprite(this.sprites);
            return particle;
        }
    }

    public static class TrailSparkParticleType extends ParticleType<TrailSparkParticleOption> {

        public TrailSparkParticleType() {
            super(false, TrailSparkParticleOption.DESERIALIZER);
        }

        @Override
        @NotNull
        public Codec<TrailSparkParticleOption> codec() {
            return Codec.unit(new TrailSparkParticleOption(0));
        }

    }
}
