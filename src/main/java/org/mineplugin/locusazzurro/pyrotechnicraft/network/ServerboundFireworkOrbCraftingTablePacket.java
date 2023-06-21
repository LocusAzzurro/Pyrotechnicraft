package org.mineplugin.locusazzurro.pyrotechnicraft.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkOrbCraftingTableBlockEntity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public record ServerboundFireworkOrbCraftingTablePacket(BlockPos craftingTablePos) {

    public void encode(FriendlyByteBuf buf){
        buf.writeBlockPos(craftingTablePos);
    }

    public static ServerboundFireworkOrbCraftingTablePacket decode(FriendlyByteBuf buf){
        return new ServerboundFireworkOrbCraftingTablePacket(buf.readBlockPos());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx){
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getSender() != null
            && ctx.get().getSender().level().getBlockEntity(craftingTablePos)
                    instanceof FireworkOrbCraftingTableBlockEntity craftingTable){
                craftingTable.craftFireworkOrb();
                success.set(true);
            }
        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }

}
