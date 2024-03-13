package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkLauncherStandBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkMissileCraftingTableBlockEntity;

import java.util.ArrayList;
import java.util.List;

public class FireworkLauncherStandContainer extends AbstractFireworkContainer{

    public static final int SLOT_ID_START = FireworkLauncherStandBlockEntity.SLOT_ID_START; //0
    public static final int SLOT_ID_END = FireworkLauncherStandBlockEntity.SLOT_ID_END; //9
    private static final int INV_SLOT_START = 9;
    private static final int INV_SLOT_END = 36;
    private static final int USE_ROW_SLOT_START = 36;
    private static final int USE_ROW_SLOT_END = 45;

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
        if (blockEntity != null) blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> addFireworkSlots(handler, 8, 18));
        this.rotationData = DataSlot.forContainer(data, 0);
        this.addDataSlot(rotationData);
        this.angleData = DataSlot.forContainer(data, 1);
        this.addDataSlot(angleData);
        layoutPlayerInventorySlots(8, 140);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot.hasItem()){
            ItemStack slotItem = slot.getItem();
            itemstack = slotItem.copy();
            if (pIndex > SLOT_ID_END || pIndex < SLOT_ID_START){
                if (FireworkLauncherStandBlockEntity.isMissileOrRocket.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, SLOT_ID_START, SLOT_ID_END + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (!this.moveItemStackTo(slotItem, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotItem.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, slotItem);
        }

        return itemstack;
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
