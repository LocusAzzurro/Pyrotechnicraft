package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Pyrotechnicraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Pyrotechnicraft.MOD_ID);

    private static ImmutableList<Object> FIREWORK_EFFECTORS = ImmutableList.of(
            Items.DIAMOND, Items.GLOWSTONE_DUST, Items.FIRE_CHARGE, Items.GOLD_NUGGET, Items.CREEPER_HEAD, Items.FEATHER
    );
    private static ImmutableList<Object> FIREWORK_BASE_ITEMS = ImmutableList.of(
            Items.GUNPOWDER, Items.PAPER
    );

    public static RegistryObject<CreativeModeTab> FIREWORKS = CREATIVE_TABS.register("fireworks",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pyrotechnicraft"))
                    .icon(ItemRegistry.FIREWORK_MISSILE.get()::getDefaultInstance)
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .withLabelColor(0xa93254)
                    .displayItems((displayParameters, output) -> ItemRegistry.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(output::accept))
                    .build());
    public static RegistryObject<CreativeModeTab> FIREWORK_INGREDIENTS = CREATIVE_TABS.register("firework_ingredients",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pyrotechnicraft.ingredients"))
                    .icon(Items.FIREWORK_ROCKET::getDefaultInstance)
                    .withTabsBefore(FIREWORKS.getKey())
                    .withLabelColor(0xa93254)
                    .displayItems((displayParameters, output) -> {
                        ForgeRegistries.ITEMS.getEntries().stream().map(Map.Entry::getValue).filter(item -> item.getDefaultInstance().is(Tags.Items.DYES)).forEach(output::accept);
                        FIREWORK_BASE_ITEMS.forEach(item -> output.accept(((Item)item).getDefaultInstance()));
                        FIREWORK_EFFECTORS.forEach(item -> output.accept(((Item)item).getDefaultInstance()));
                    })
                    .build());




    @SubscribeEvent
    public static void buildCreativeTab(BuildCreativeModeTabContentsEvent event){

    }
}
