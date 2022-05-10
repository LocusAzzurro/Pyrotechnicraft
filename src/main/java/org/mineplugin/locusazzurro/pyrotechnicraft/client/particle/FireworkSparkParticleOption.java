package org.mineplugin.locusazzurro.pyrotechnicraft.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ParticleTypeRegistry;

public record FireworkSparkParticleOption(int color, int fadeColor, boolean trail, boolean sparkle) implements ParticleOptions {

    @SuppressWarnings("deprecation")
    public static final ParticleOptions.Deserializer<FireworkSparkParticleOption> DESERIALIZER =
            new Deserializer<>() {
                @Override
                public FireworkSparkParticleOption fromCommand(ParticleType<FireworkSparkParticleOption> pParticleType, StringReader pReader) throws CommandSyntaxException {
                    int color, fade;
                    pReader.expect(' ');
                    color = pReader.readInt();
                    pReader.expect(' ');
                    fade = pReader.readInt();
                    return new FireworkSparkParticleOption(color, fade, false, false);
                }

                @Override
                public FireworkSparkParticleOption fromNetwork(ParticleType<FireworkSparkParticleOption> pParticleType, FriendlyByteBuf pBuffer) {
                    int color = pBuffer.readInt();
                    int fade = pBuffer.readInt();
                    boolean trail = pBuffer.readBoolean();
                    boolean sparkle = pBuffer.readBoolean();
                    return new FireworkSparkParticleOption(color, fade, trail, sparkle);
                }
            };

    @Override
    @NotNull
    public ParticleType<?> getType() {
        return ParticleTypeRegistry.FIREWORK_SPARK.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeInt(color);
        pBuffer.writeInt(fadeColor);
        pBuffer.writeBoolean(trail);
        pBuffer.writeBoolean(sparkle);
    }

    @Override
    @NotNull
    public String writeToString() {
        return "[FireworkSpark / Color: " + this.color + " / FadeColor: " +  this.fadeColor + " ]";
    }
}
