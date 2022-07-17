package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkLauncherStandBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkMissileCraftingTableBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkOrbCraftingTableBlockEntity;

public class BlockEntityTypeRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Pyrotechnicraft.MOD_ID);
    public static final RegistryObject<BlockEntityType<FireworkOrbCraftingTableBlockEntity>> FIREWORK_ORB_CRAFTING_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("firework_orb_crafting_table_block_entity", () -> BlockEntityType.Builder
                    .of(FireworkOrbCraftingTableBlockEntity::new, BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<FireworkMissileCraftingTableBlockEntity>> FIREWORK_MISSILE_CRAFTING_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("firework_missile_crafting_table_block_entity", () -> BlockEntityType.Builder
                    .of(FireworkMissileCraftingTableBlockEntity::new, BlockRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<FireworkLauncherStandBlockEntity>> FIREWORK_LAUNCHER_STAND_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("firework_launcher_stand_block_entity", () -> BlockEntityType.Builder
                    .of(FireworkLauncherStandBlockEntity::new, BlockRegistry.FIREWORK_LAUNCHER_STAND.get()).build(null));



}
