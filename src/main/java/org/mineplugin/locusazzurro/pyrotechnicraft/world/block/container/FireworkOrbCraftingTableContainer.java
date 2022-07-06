package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;

public class FireworkOrbCraftingTableContainer extends AbstractContainerMenu {

    private final BlockEntity blockEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;

    public FireworkOrbCraftingTableContainer(int pContainerId, BlockPos pos, Inventory playerInventory, Player player) {
        super(ContainerTypeRegistry.FIREWORK_ORB_CRAFTING_TABLE.get(), pContainerId);
        blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        if (blockEntity != null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> addSlot(new SlotItemHandler(handler, 0, 64, 24)));
        }
        layoutPlayerInventorySlots(8, 140);

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE.get());
    }

    public BlockEntity getBlockEntity(){
        return blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            itemstack = slotItem.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(slotItem, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotItem, itemstack);
            } else {
                if (!this.moveItemStackTo(slotItem, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
                else if (index < 28) {
                    if (!this.moveItemStackTo(slotItem, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(slotItem, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
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

    public boolean hasValidItems(){
        return this.getSlot(0).hasItem();
    }

    private int addSlotRow(IItemHandler handler, int startIndex, int xo, int yo, int slotCount, int dx) {
        int x = xo;
        int y = yo;
        int index = startIndex;
        for (int i = 0 ; i < slotCount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int startIndex, int xo, int yo, int horSlotCount, int dx, int verSlotCount, int dy) {
        int x = xo;
        int y = yo;
        int index = startIndex;
        for (int j = 0 ; j < verSlotCount ; j++) {
            index = addSlotRow(handler, index, x, y, horSlotCount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int startX, int startY) {
        // Player inventory
        addSlotBox(playerInventory, 9, startX, startY, 9, 18, 3, 18);

        // Hotbar
        startY += 58;
        addSlotRow(playerInventory, 0, startX, startY, 9, 18);
    }
}
