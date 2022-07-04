package org.mineplugin.locusazzurro.pyrotechnicraft.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.screen.FireworkOrbCraftingTableScreen;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerTypeRegistry.FIREWORK_ORB_CRAFTING_TABLE.get(), FireworkOrbCraftingTableScreen::new);
        });
    }
}
