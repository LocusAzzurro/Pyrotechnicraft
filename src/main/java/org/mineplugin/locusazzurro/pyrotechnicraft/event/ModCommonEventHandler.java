package org.mineplugin.locusazzurro.pyrotechnicraft.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.PacketHandler;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEventHandler {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::init);
    }

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event){
        event.registerCreativeModeTab(new ResourceLocation(Pyrotechnicraft.MOD_ID, "firework"), builder -> builder
                .icon(() -> new ItemStack(ItemRegistry.FIREWORK_MISSILE.get()))
                .title(Component.translatable("itemGroup.pyrotechnicraft"))
                .displayItems((featureFlags, output, hasOp) -> {
                    ItemRegistry.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(output::accept);
                })
        );
    }

}
