package org.mineplugin.locusazzurro.pyrotechnicraft.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ParticleTypeRegistry;

import net.minecraft.core.particles.ParticleOptions.Deserializer;

public record TrailSparkParticleOption(int color) implements ParticleOptions {

    @SuppressWarnings("deprecation")
    public static final ParticleOptions.Deserializer<TrailSparkParticleOption> DESERIALIZER =
            new Deserializer<>() {
                @Override
                public TrailSparkParticleOption fromCommand(ParticleType<TrailSparkParticleOption> pParticleType, StringReader pReader) throws CommandSyntaxException {
                    int color;
                    pReader.expect(' ');
                    color = pReader.readInt();
                    return new TrailSparkParticleOption(color);
                }

                @Override
                public TrailSparkParticleOption fromNetwork(ParticleType<TrailSparkParticleOption> pParticleType, FriendlyByteBuf pBuffer) {
                    int color = pBuffer.readInt();
                    return new TrailSparkParticleOption(color);
                }
            };

    @Override
    @NotNull
    public ParticleType<?> getType() {
        return ParticleTypeRegistry.TRAIL_SPARK.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeInt(color);
    }

    @Override
    @NotNull
    public String writeToString() {
        return "[TrailSpark / Color: " + this.color + " ]";
    }
}
