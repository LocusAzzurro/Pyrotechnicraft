package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
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
        singleTexture(ItemRegistry.FIREWORK_ORB_CORE.get().getRegistryName().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/firework_orb_core"));
    }

}
