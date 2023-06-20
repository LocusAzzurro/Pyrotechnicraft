package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ModItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, TagsProvider<Block> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, DataGenerators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(ModItemTags.FORGE_SULFUR).add(ItemRegistry.SULFUR.get());
        tag(ModItemTags.FORGE_SALTPETER).add(ItemRegistry.SALTPETER.get());
        tag(ModItemTags.FORGE_COAL_DUST).add(ItemRegistry.COAL_DUST.get());
        tag(ModItemTags.FORGE_IRON_DUST);
        tag(ModItemTags.FORGE_DIAMOND_DUST);
        tag(ModItemTags.TRAIL_EFFECT_ITEMS)
                .addTag(ModItemTags.FORGE_DIAMOND_DUST)
                .add(Items.DIAMOND);
        tag(ModItemTags.SPARKLE_EFFECT_ITEMS)
                .addTag(ModItemTags.FORGE_IRON_DUST)
                .add(Items.GLOWSTONE_DUST);
    }

    @Override
    public String getName() {
        return DataGenerators.MOD_ID + " Item Tags";
    }

}
