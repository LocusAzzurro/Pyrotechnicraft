package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.BasicMaterialItem;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.FireworkLighter;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.FlickerStick;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Pyrotechnicraft.MOD_ID);

    public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur", BasicMaterialItem::new);
    public static final RegistryObject<Item> SALTPETER = ITEMS.register("saltpeter", BasicMaterialItem::new);
    public static final RegistryObject<Item> COAL_DUST = ITEMS.register("coal_dust", BasicMaterialItem::new);
    public static final RegistryObject<Item> FIREWORK_ORB_CORE = ITEMS.register("firework_orb_core", BasicMaterialItem::new);
    public static final RegistryObject<Item> FIREWORK_LIGHTER = ITEMS.register("firework_lighter", FireworkLighter::new);
    public static final RegistryObject<Item> FLICKER_STICK = ITEMS.register("flicker_stick", FlickerStick::new);
    public static final RegistryObject<Item> FIREWORK_LAUNCHER_STAND = fromBlock(BlockRegistry.FIREWORK_LAUNCHER_STAND);
    public static final RegistryObject<Item> FIREWORK_ORB_CRAFTING_TABLE = fromBlock(BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE);

    private static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(Pyrotechnicraft.CREATIVE_TAB)));
    }

}
