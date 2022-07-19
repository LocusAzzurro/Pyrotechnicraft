package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;

public class ModBlockModelProvider extends BlockStateProvider {

    public ModBlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DataGenerators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE.get(), models().orientableWithBottom(
                BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE.get().getRegistryName().getPath(),
                modLoc("block/firework_crafting_table_side"),
                modLoc("block/firework_crafting_table_side"),
                modLoc("block/firework_crafting_table_bottom"),
                modLoc("block/firework_orb_crafting_table_top")
        ));
        horizontalBlock(BlockRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get(), models().orientableWithBottom(
                BlockRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get().getRegistryName().getPath(),
                modLoc("block/firework_crafting_table_side"),
                modLoc("block/firework_crafting_table_side"),
                modLoc("block/firework_crafting_table_bottom"),
                modLoc("block/firework_missile_crafting_table_top")
        ));
    }
}
