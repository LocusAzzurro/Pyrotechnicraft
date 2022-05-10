package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockEntityTypeRegistry;

public class FireworkLauncherStandBlockEntity extends BlockEntity {

    public FireworkLauncherStandBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.FIREWORK_LAUNCHER_STAND_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }
}
