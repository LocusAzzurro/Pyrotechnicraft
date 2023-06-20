package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    public ModBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE.get());
        dropSelf(BlockRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get());
        dropSelf(BlockRegistry.FIREWORK_LAUNCHER_STAND.get());
        dropSelf(BlockRegistry.COMPOSITE_FIREWORK_ORB_LAUNCHER_STAND.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
