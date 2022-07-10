package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkOrbCraftingTableBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.ExplosionShape;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.ExplosionShapePattern;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FireworkOrbCraftingTableContainer extends AbstractContainerMenu {

    private final BlockEntity blockEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;
    private CoreSlot coreSlot;
    private PatternSlot patternSlot;
    private List<AmplifierSlot> amplifierSlotList = new ArrayList<AmplifierSlot>();
    private AmplifierSlot forceSlot;
    private AmplifierSlot sparkSlot;
    private AmplifierSlot damageSlot;
    private EffectorSlot sparkleEffectSlot;
    private EffectorSlot trailEffectSlot;
    private List<DyeSlot> colorSlotList = new ArrayList<DyeSlot>();
    private List<DyeSlot> fadeColorSlotList = new ArrayList<DyeSlot>();
    private OutputSlot outputSlot;

    public FireworkOrbCraftingTableContainer(int pContainerId, BlockPos pos, Inventory playerInventory, Player player) {
        super(ContainerTypeRegistry.FIREWORK_ORB_CRAFTING_TABLE.get(), pContainerId);
        blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        if (blockEntity != null) blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(this::addCraftingSlots);
        layoutPlayerInventorySlots(8, 140);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE.get());
    }

    public BlockEntity getBlockEntity(){
        return blockEntity;
    }

    /*
    Crafting Table Slot = 0 ~ 23
    Player Inventory = 24 ~ 50
    Player Hot-bar = 51 ~ 59
     */
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int index) {
        return ItemStack.EMPTY;
    }

    public boolean hasValidItemsForCrafting(){
        return this.slots.stream().allMatch(Objects::nonNull)
                && this.coreSlot.hasItem()
                && this.patternSlot.hasItem()
                && this.amplifierSlotList.stream().allMatch(Slot::hasItem)
                && this.colorSlotList.stream().anyMatch(Slot::hasItem)
                && this.sparkSlot.getItem().getCount() >= getPatternCost(patternSlot.getItem())
                && !this.outputSlot.hasItem();
    }

    public static int getPatternCost(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        Item item = itemStack.getItem();
        if (item instanceof ExplosionShapePattern pattern
                && (pattern.getShape() == ExplosionShape.PLANE || pattern.getShape() == ExplosionShape.MATRIX)){
            if (tag.contains("Cost")) return tag.getInt("Cost");
            else return 16;
        }
        else return 0;
    }

    class OutputSlot extends SlotItemHandler{
        public OutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return false;
        }
    }

    class CoreSlot extends SlotItemHandler {
        public CoreSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }

    class PatternSlot extends SlotItemHandler{
        public PatternSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }

    class AmplifierSlot extends SlotItemHandler{
        public AmplifierSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public int getMaxStackSize() {
            return 16;
        }
    }

    class EffectorSlot extends SlotItemHandler{
        public EffectorSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }

    class DyeSlot extends SlotItemHandler{
        public DyeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }

    private void addCraftingSlots (IItemHandler handler){
        this.coreSlot = new CoreSlot(handler, FireworkOrbCraftingTableBlockEntity.CORE_SLOT_ID, 44, 18);
        addSlot(coreSlot);
        this.patternSlot = new PatternSlot(handler, FireworkOrbCraftingTableBlockEntity.PATTERN_SLOT_ID, 8, 18);
        addSlot(patternSlot);
        this.forceSlot = new AmplifierSlot(handler, FireworkOrbCraftingTableBlockEntity.FORCE_SLOT_ID, 26, 40);
        addSlot(forceSlot);
        this.sparkSlot = new AmplifierSlot(handler, FireworkOrbCraftingTableBlockEntity.SPARK_SLOT_ID, 44, 40);
        addSlot(sparkSlot);
        this.damageSlot = new AmplifierSlot(handler, FireworkOrbCraftingTableBlockEntity.DAMAGE_SLOT_ID, 62, 40);
        addSlot(damageSlot);
        this.amplifierSlotList.addAll(Arrays.asList(forceSlot, sparkSlot, damageSlot));
        this.trailEffectSlot = new EffectorSlot(handler, FireworkOrbCraftingTableBlockEntity.TRAIL_SLOT_ID, 35, 58);
        addSlot(trailEffectSlot);
        this.sparkleEffectSlot = new EffectorSlot(handler, FireworkOrbCraftingTableBlockEntity.SPARKLE_SLOT_ID, 53, 58);
        addSlot(sparkleEffectSlot);
        addColorSlots(handler, 98, 36);
        addFadeColorSlots(handler, 98, 90);
        this.outputSlot = new OutputSlot(handler, FireworkOrbCraftingTableBlockEntity.OUTPUT_SLOT_ID, 44, 104);
        addSlot(outputSlot);
    }

    private void addColorSlots(IItemHandler handler, int xo, int yo){
        int startID = FireworkOrbCraftingTableBlockEntity.COLOR_SLOT_ID_START;
        for (int row = 0; row < 2; row++){
            for (int col = 0; col < 4; col++){
                DyeSlot slot = new DyeSlot(handler, startID + 4 * row + col, xo + col * 18, yo + row * 18);
                addSlot(slot);
                this.colorSlotList.add(slot);
            }
        }
    }
    private void addFadeColorSlots(IItemHandler handler, int xo, int yo){
        int startID = FireworkOrbCraftingTableBlockEntity.FADE_COLOR_SLOT_ID_START;
        for (int row = 0; row < 2; row++){
            for (int col = 0; col < 4; col++){
                DyeSlot slot = new DyeSlot(handler, startID + 4 * row + col, xo + col * 18, yo + row * 18);
                addSlot(slot);
                this.fadeColorSlotList.add(slot);
            }
        }
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
