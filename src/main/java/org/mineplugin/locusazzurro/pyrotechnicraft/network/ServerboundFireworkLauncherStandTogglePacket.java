package org.mineplugin.locusazzurro.pyrotechnicraft.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkLauncherStandBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkMissileCraftingTableBlockEntity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public record ServerboundFireworkLauncherStandTogglePacket(BlockPos launcherPos, byte data, short value) {

    public void encode(FriendlyByteBuf buf){
        buf.writeBlockPos(launcherPos);
        buf.writeByte(data);
        buf.writeShort(value);
    }

    public static ServerboundFireworkLauncherStandTogglePacket decode(FriendlyByteBuf buf){
        return new ServerboundFireworkLauncherStandTogglePacket(buf.readBlockPos(), buf.readByte(), buf.readShort());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx){
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getSender() != null
                    && ctx.get().getSender().getLevel().getBlockEntity(launcherPos)
                    instanceof FireworkLauncherStandBlockEntity launcher){
                launcher.setData(data, value);
                success.set(true);
            }
        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
