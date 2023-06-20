package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DataGenerators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
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
