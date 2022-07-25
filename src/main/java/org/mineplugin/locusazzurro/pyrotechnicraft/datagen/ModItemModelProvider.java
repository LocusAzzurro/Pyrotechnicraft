package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;
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

        generatedItem(ItemRegistry.HOMING_ARRAY_SCRIPT.get());
        generatedItem(ItemRegistry.TACTICAL_SCRIPT.get());
        generatedItem(ItemRegistry.FIRECRACKER.get());
        generatedItem(ItemRegistry.FIREWORK_REPLICATION_KIT.get());
        generatedItem(ItemRegistry.CREATIVE_FIREWORK_TEST_KIT.get());
        blockItem(BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE.get());
        blockItem(BlockRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get());
        blockItem(BlockRegistry.FIREWORK_LAUNCHER_STAND.get());
    }

    private ItemModelBuilder generatedItem(Item item){
        return singleTexture(item.getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder blockItem(Block block){
        return withExistingParent(block.getRegistryName().getPath(), modLoc("block/" + block.getRegistryName().getPath()));
    }

    @NotNull
    @Override
    public String getName() {
        return DataGenerators.MOD_ID + " Item Models";
    }
}
