package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkLauncherStandBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkMissileCraftingTableBlockEntity;

import java.util.ArrayList;
import java.util.List;

public class FireworkLauncherStandContainer extends AbstractFireworkContainer{

    private final ContainerData data;
    private DataSlot rotationData;
    private DataSlot angleData;
    private List<FireworkSlot> fireworkSlotList = new ArrayList<>();

    public FireworkLauncherStandContainer(int pContainerId, BlockPos pos, Inventory playerInventory, Player player) {
        this(pContainerId, pos, playerInventory, player, new SimpleContainerData(2));
    }
    public FireworkLauncherStandContainer(int pContainerId, BlockPos pos, Inventory playerInventory, Player player, ContainerData containerData) {
        super(ContainerTypeRegistry.FIREWORK_LAUNCHER_STAND.get(), pContainerId, pos, playerInventory, player);
        this.data = containerData;
        if (blockEntity != null) blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> addFireworkSlots(handler, 8, 18));
        this.rotationData = DataSlot.forContainer(data, 0);
        this.addDataSlot(rotationData);
        this.angleData = DataSlot.forContainer(data, 1);
        this.addDataSlot(angleData);
        layoutPlayerInventorySlots(8, 140);
    }

    private void addFireworkSlots(IItemHandler handler, int xo, int yo) {
        int startID = FireworkLauncherStandBlockEntity.SLOT_ID_START;
        for (int i = 0; i < FireworkLauncherStandBlockEntity.SLOT_COUNT; i++){
            FireworkSlot fireworkSlot = new FireworkSlot(handler, startID + i, xo + i * 18, yo);
            addSlot(fireworkSlot);
            fireworkSlotList.add(fireworkSlot);
        }
    }

    public int getRotation(){
        return this.rotationData.get();
    }

    public int getAngle(){
        return this.angleData.get();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockRegistry.FIREWORK_LAUNCHER_STAND.get());
    }

    static class FireworkSlot extends SlotItemHandler {
        public FireworkSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }
}
