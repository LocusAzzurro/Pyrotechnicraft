package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ModItemTags;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, DataGenerators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
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
