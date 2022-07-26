package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public abstract class AbstractFireworkContainer extends AbstractContainerMenu {

    protected final BlockEntity blockEntity;
    protected Player playerEntity;
    protected IItemHandler playerInventory;

    protected AbstractFireworkContainer(@Nullable MenuType<?> pMenuType, int pContainerId, BlockPos pos, Inventory playerInventory, Player player) {
        super(pMenuType, pContainerId);
        blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
    }

    @Override
    @NotNull
    public ItemStack quickMoveStack(Player pPlayer, int index) {
        return ItemStack.EMPTY;
    }

    public BlockEntity getBlockEntity(){
        return blockEntity;
    }

    protected int addSlotRow(IItemHandler handler, int startIndex, int xo, int yo, int slotCount, int dx) {
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

    protected int addSlotBox(IItemHandler handler, int startIndex, int xo, int yo, int horSlotCount, int dx, int verSlotCount, int dy) {
        int x = xo;
        int y = yo;
        int index = startIndex;
        for (int j = 0 ; j < verSlotCount ; j++) {
            index = addSlotRow(handler, index, x, y, horSlotCount, dx);
            y += dy;
        }
        return index;
    }

    protected void layoutPlayerInventorySlots(int startX, int startY) {
        // Player inventory
        addSlotBox(playerInventory, 9, startX, startY, 9, 18, 3, 18);

        // Hotbar
        startY += 58;
        addSlotRow(playerInventory, 0, startX, startY, 9, 18);
    }

    static class OutputSlot extends SlotItemHandler{
        public OutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return false;
        }
    }

    static class CoreSlot extends SlotItemHandler {
        public CoreSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }

    static class PatternSlot extends SlotItemHandler{
        public PatternSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }

    static class AmplifierSlot extends SlotItemHandler{
        public AmplifierSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public int getMaxStackSize() {
            return 16;
        }
    }

    static class EffectorSlot extends SlotItemHandler{
        public EffectorSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }

    static class DyeSlot extends SlotItemHandler{
        public DyeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }

    static class ModuleSlot extends SlotItemHandler {

        public ModuleSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }

    static class FuseSlot extends SlotItemHandler {

        public FuseSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }

    static class WrappingMaterialSlot extends SlotItemHandler {

        public WrappingMaterialSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }

    static class StarSlot extends SlotItemHandler {

        public StarSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }
}
