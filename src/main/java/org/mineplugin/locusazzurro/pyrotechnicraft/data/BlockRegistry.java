package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.FireworkLauncherStand;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.FireworkLighter;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Pyrotechnicraft.MOD_ID);

    public static final RegistryObject<Block> FIREWORK_LAUNCHER_STAND = BLOCKS.register("firework_launcher_stand", FireworkLauncherStand::new);

}
