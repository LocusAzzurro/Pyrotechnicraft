package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DataGenerators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        generatedItem(ItemRegistry.SULFUR.get());
        generatedItem(ItemRegistry.SALTPETER.get());
        generatedItem(ItemRegistry.COAL_DUST.get());
        generatedItem(ItemRegistry.FIREWORK_MIXTURE.get());
        generatedItem(ItemRegistry.FIREWORK_ORB_CORE.get());
        generatedItem(ItemRegistry.SPHERE_EXPLOSION_PATTERN.get());
        generatedItem(ItemRegistry.RING_EXPLOSION_PATTERN.get());
        generatedItem(ItemRegistry.BURST_EXPLOSION_PATTERN.get());
        generatedItem(ItemRegistry.PLANE_EXPLOSION_PATTERN.get());
        generatedItem(ItemRegistry.MATRIX_EXPLOSION_PATTERN.get());
        generatedItem(ItemRegistry.INSTANT_FUSE.get());
        generatedItem(ItemRegistry.REGULAR_FUSE.get());
        generatedItem(ItemRegistry.EXTENDED_FUSE.get());
        generatedItem(ItemRegistry.CUSTOM_FUSE.get());
        generatedItem(ItemRegistry.FIREWORK_HOMING_MODULE.get());
        generatedItem(ItemRegistry.FIREWORK_WRAPPING_PAPER.get());
    }

    private ItemModelBuilder generatedItem(Item item){
        return singleTexture(item.getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + item.getRegistryName().getPath()));
    }

    @NotNull
    @Override
    public String getName() {
        return DataGenerators.MOD_ID + " Item Models";
    }
}
