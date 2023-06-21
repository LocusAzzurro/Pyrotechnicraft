package org.mineplugin.locusazzurro.pyrotechnicraft.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkMissileCraftingTableBlockEntity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public record ServerboundFireworkMissileCraftingTablePacket(BlockPos craftingTablePos){

    public void encode(FriendlyByteBuf buf){
        buf.writeBlockPos(craftingTablePos);
    }

    public static ServerboundFireworkMissileCraftingTablePacket decode(FriendlyByteBuf buf){
        return new ServerboundFireworkMissileCraftingTablePacket(buf.readBlockPos());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx){
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getSender() != null
                    && ctx.get().getSender().level().getBlockEntity(craftingTablePos)
                    instanceof FireworkMissileCraftingTableBlockEntity craftingTable){
                craftingTable.craftFireworkMissile();
                success.set(true);
            }
        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
