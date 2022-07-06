package org.mineplugin.locusazzurro.pyrotechnicraft.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";
    private static int ID = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Pyrotechnicraft.MOD_ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void init() {
        INSTANCE.messageBuilder(ServerboundFireworkOrbCraftingTablePacket.class, ID++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ServerboundFireworkOrbCraftingTablePacket::encode)
                .decoder(ServerboundFireworkOrbCraftingTablePacket::decode)
                .consumer(ServerboundFireworkOrbCraftingTablePacket::handle)
                .add();
    }
}
