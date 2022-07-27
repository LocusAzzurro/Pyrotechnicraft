package org.mineplugin.locusazzurro.pyrotechnicraft.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.util.ColorUtil;

public class FireworkSparkParticle extends TextureSheetParticle {

    private final SpriteSet sprites;
    private final ParticleEngine engine;
    private static final int LOOP_SPEED = 10;
    private static final int SPRITE_COUNT = 5;
    private int color;
    private int fadeColor;
    private float inertia = 0.9f;
    private float volatility = 0.95f;
    private boolean trail = false;
    private boolean sparkle = false;

    public FireworkSparkParticle(ClientLevel level, Vec3 pos, Vec3 mov, int color, int fadeColor, SpriteSet sprites, ParticleEngine engine) {
        super(level, pos.x, pos.y, pos.z, mov.x, mov.y, mov.z);
        this.setColor(ColorUtil.redF(color), ColorUtil.greenF(color), ColorUtil.blueF(color));
        this.color = color;
        this.fadeColor = fadeColor;
        this.lifetime = 48 + this.random.nextInt(12);
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
        this.xd = mov.x;
        this.yd = mov.y;
        this.zd = mov.z;
        this.hasPhysics = true;
        this.sprites = sprites;
        this.engine = engine;
    }

    public void setInertia(float inertia){
        this.inertia = Mth.clamp(inertia, 0f, 1f);
    }

    public void setVolatility(float volatility){
        this.volatility = Mth.clamp(volatility, 0f, 1f);
    }

    public void trail(){
        this.trail = true;
    }

    public void sparkle(){
        this.sparkle = true;
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick(){

        //first number must not exceed second by 1 + 1/sprite amount
        this.setSprite(this.sprites.get(this.age % (LOOP_SPEED + LOOP_SPEED / SPRITE_COUNT), LOOP_SPEED));

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        int timeLeft = this.lifetime - this.age++;
        if (timeLeft <= 0) this.remove();
        else {
            this.yd -= (1 - this.volatility) * 0.1;
            this.move(xd, yd, zd);
            this.xd *= this.inertia;
            this.yd *= this.inertia;
            this.zd *= this.inertia;
        }

        float alpha = 1.0f;

        if (timeLeft <= 20) {
            this.setColor(
                    (float) Mth.lerp(0.1, this.rCol, ColorUtil.redF(this.fadeColor)),
                    (float) Mth.lerp(0.1, this.gCol, ColorUtil.greenF(this.fadeColor)),
                    (float) Mth.lerp(0.1, this.bCol, ColorUtil.blueF(this.fadeColor))
            );
        }

        if (timeLeft < 10){
            alpha = 0.1f * timeLeft;
        }

        if(sparkle && this.age % 3 == 0 && timeLeft <= 15){
            alpha = 0.0f;
        }

        this.setAlpha(alpha);

        float speed = Mth.sqrt((float) (xd * xd + yd * yd + zd * zd));
        if(trail && (speed >= 0.5 || (speed < 0.5 && this.age % 2 == 0))){
            FireworkSparkParticle trailParticle =
                    new FireworkSparkParticle(this.level, new Vec3(this.x, this.y, this.z), new Vec3(0,0,0),
                            this.color, this.fadeColor, this.sprites, this.engine);
            trailParticle.setSprite(this.sprites.get(trailParticle.age % 50, 50));
            trailParticle.setInertia(1f);
            trailParticle.setVolatility(this.volatility);
            trailParticle.lifetime = 12;
            this.engine.add(trailParticle);
        }
    }

    @Override
    public int getLightColor(float pPartialTick) {
        return 15728880;
    }

    @OnlyIn(Dist.CLIENT)
    public static class FireworkSparkProvider implements ParticleProvider<FireworkSparkParticleOption> {

        private final SpriteSet sprites;
        public FireworkSparkProvider(SpriteSet sprite) {
            this.sprites = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(FireworkSparkParticleOption option, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            FireworkSparkParticle particle = new FireworkSparkParticle(pLevel, new Vec3(pX, pY, pZ), new Vec3(pXSpeed, pYSpeed, pZSpeed),
                    option.color(), option.fadeColor(), this.sprites, Minecraft.getInstance().particleEngine);
            if (option.trail()) particle.trail();
            if (option.sparkle()) particle.sparkle();
            particle.setSpriteFromAge(this.sprites);
            return particle;
        }
    }

    public static class FireworkSparkParticleType extends ParticleType<FireworkSparkParticleOption> {


        public FireworkSparkParticleType() {
            super(true, FireworkSparkParticleOption.DESERIALIZER);
        }

        @Override
        @NotNull
        public Codec<FireworkSparkParticleOption> codec() {
            return Codec.unit(new FireworkSparkParticleOption(0, 0, false, false));
        }

    }
}
