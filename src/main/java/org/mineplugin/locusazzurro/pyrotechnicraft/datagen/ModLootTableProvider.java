package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;

public class ModLootTableProvider extends BaseLootTableProvider {

    public ModLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void addTables() {
        simple(BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE);
        simple(BlockRegistry.FIREWORK_MISSILE_CRAFTING_TABLE);
        simple(BlockRegistry.FIREWORK_LAUNCHER_STAND);
        simple(BlockRegistry.COMPOSITE_FIREWORK_ORB_LAUNCHER_STAND);
    }

    void simple(RegistryObject<? extends Block> block) {
        lootTables.put(block.get(), createSimpleTable(block.getId().getPath(), block.get()));
    }

}
