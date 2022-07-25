package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, DataGenerators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE.get())
                .add(BlockRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockRegistry.FIREWORK_LAUNCHER_STAND.get())
                .add(BlockRegistry.COMPOSITE_FIREWORK_ORB_LAUNCHER_STAND.get());
    }

    @Override
    public String getName() {
        return DataGenerators.MOD_ID + " Block Tags";
    }
}
