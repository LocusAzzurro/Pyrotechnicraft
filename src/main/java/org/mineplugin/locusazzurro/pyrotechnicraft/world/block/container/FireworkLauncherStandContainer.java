package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkLauncherStandBlockEntity;

public class FireworkLauncherStandContainer extends AbstractFireworkContainer{

    private final ContainerData data;
    public FireworkLauncherStandContainer(int pContainerId, BlockPos pos, Inventory playerInventory, Player player) {
        this(pContainerId, pos, playerInventory, player, new SimpleContainerData(2));
    }
    public FireworkLauncherStandContainer(int pContainerId, BlockPos pos, Inventory playerInventory, Player player, ContainerData containerData) {
        super(ContainerTypeRegistry.FIREWORK_LAUNCHER_STAND.get(), pContainerId, pos, playerInventory, player);
        this.data = containerData;
        //if (blockEntity != null) blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(this::addCraftingSlots);
        layoutPlayerInventorySlots(8, 140);
    }

    public int getRotation(){
        if (this.blockEntity instanceof FireworkLauncherStandBlockEntity launcher){
            return launcher.containerData.get(FireworkLauncherStandBlockEntity.ROTATION_DATA_SLOT_ID);
        }
        return 0;
    }

    public int getAngle(){
        if (this.blockEntity instanceof FireworkLauncherStandBlockEntity launcher){
            return launcher.containerData.get(FireworkLauncherStandBlockEntity.ANGLE_DATA_SLOT_ID);
        }
        return 0;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockRegistry.FIREWORK_LAUNCHER_STAND.get());
    }
}
