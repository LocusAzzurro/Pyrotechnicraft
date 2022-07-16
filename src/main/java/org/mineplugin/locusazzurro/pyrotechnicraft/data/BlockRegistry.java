package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.*;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Pyrotechnicraft.MOD_ID);

    public static final RegistryObject<Block> FIREWORK_ORB_CRAFTING_TABLE = BLOCKS.register("firework_orb_crafting_table", FireworkOrbCraftingTable::new);
    public static final RegistryObject<Block> FIREWORK_MISSILE_CRAFTING_TABLE = BLOCKS.register("firework_missile_crafting_table", FireworkMissileCraftingTable::new);
    public static final RegistryObject<Block> FIREWORK_LAUNCHER_STAND = BLOCKS.register("firework_launcher_stand", FireworkLauncherStand::new);
    public static final RegistryObject<Block> COMPOSITE_FIREWORK_ORB_LAUNCHER_STAND = BLOCKS.register("composite_firework_orb_launcher_stand", CompositeFireworkOrbLauncherStand::new);

}
