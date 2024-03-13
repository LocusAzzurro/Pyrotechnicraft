package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Pyrotechnicraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Pyrotechnicraft.MOD_ID);

    private static List<Item> FIREWORK_EFFECTORS = ImmutableList.of(
            Items.DIAMOND, Items.GLOWSTONE_DUST, Items.FIRE_CHARGE, Items.GOLD_NUGGET, Items.CREEPER_HEAD, Items.FEATHER
    );
    private static List<Item> FIREWORK_BASE_ITEMS = ImmutableList.of(
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
                        FIREWORK_BASE_ITEMS.forEach(output::accept);
                        output.acceptAll(List.of(Ingredient.of(Tags.Items.DYES).getItems()));
                        FIREWORK_EFFECTORS.forEach(output::accept);
                    })
                    .build());




    @SubscribeEvent
    public static void buildCreativeTab(BuildCreativeModeTabContentsEvent event){

    }
}
