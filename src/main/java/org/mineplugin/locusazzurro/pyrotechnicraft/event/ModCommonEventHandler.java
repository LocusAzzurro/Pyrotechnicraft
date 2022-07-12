package org.mineplugin.locusazzurro.pyrotechnicraft.event;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.color.FireworkOrbColorer;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.PacketHandler;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEventHandler {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::init);
    }
    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event){
        ItemColors itemColors = event.getItemColors();
        itemColors.register(new FireworkOrbColorer(), ItemRegistry.FIREWORK_ORB.get());
    }
}
