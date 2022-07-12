package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.DataGenerator;
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
        singleTexture(ItemRegistry.SULFUR.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/sulfur"));
        singleTexture(ItemRegistry.SALTPETER.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/saltpeter"));
        singleTexture(ItemRegistry.COAL_DUST.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/coal_dust"));
        singleTexture(ItemRegistry.FIREWORK_MIXTURE.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/firework_mixture"));
        singleTexture(ItemRegistry.FIREWORK_ORB_CORE.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/firework_orb_core"));
        singleTexture(ItemRegistry.SPHERE_EXPLOSION_PATTERN.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/sphere_explosion_pattern"));
        singleTexture(ItemRegistry.RING_EXPLOSION_PATTERN.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/ring_explosion_pattern"));
        singleTexture(ItemRegistry.BURST_EXPLOSION_PATTERN.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/burst_explosion_pattern"));
        singleTexture(ItemRegistry.PLANE_EXPLOSION_PATTERN.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/2d_explosion_pattern"));
        singleTexture(ItemRegistry.MATRIX_EXPLOSION_PATTERN.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/3d_explosion_pattern"));
    }

    @NotNull
    @Override
    public String getName() {
        return DataGenerators.MOD_ID + " Item Models";
    }
}
