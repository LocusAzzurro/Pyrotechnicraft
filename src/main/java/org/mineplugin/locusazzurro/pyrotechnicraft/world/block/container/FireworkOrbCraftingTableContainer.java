package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkOrbCraftingTableBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.ExplosionShape;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.ExplosionShapePattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FireworkOrbCraftingTableContainer extends AbstractFireworkContainer {

    public static final int CORE_SLOT_ID = FireworkOrbCraftingTableBlockEntity.CORE_SLOT_ID; //0
    public static final int PATTERN_SLOT_ID = FireworkOrbCraftingTableBlockEntity.PATTERN_SLOT_ID; //1
    public static final int FORCE_SLOT_ID = FireworkOrbCraftingTableBlockEntity.FORCE_SLOT_ID; //2
    public static final int SPARK_SLOT_ID = FireworkOrbCraftingTableBlockEntity.SPARK_SLOT_ID; //3
    public static final int DAMAGE_SLOT_ID = FireworkOrbCraftingTableBlockEntity.DAMAGE_SLOT_ID; //4
    public static final int TRAIL_SLOT_ID = FireworkOrbCraftingTableBlockEntity.TRAIL_SLOT_ID; //5
    public static final int SPARKLE_SLOT_ID = FireworkOrbCraftingTableBlockEntity.SPARKLE_SLOT_ID; //6
    public static final int COLOR_SLOT_ID_START = FireworkOrbCraftingTableBlockEntity.COLOR_SLOT_ID_START; //7
    public static final int COLOR_SLOT_ID_END = FireworkOrbCraftingTableBlockEntity.COLOR_SLOT_ID_END; //14
    public static final int FADE_COLOR_SLOT_ID_START = FireworkOrbCraftingTableBlockEntity.FADE_COLOR_SLOT_ID_START; //15
    public static final int FADE_COLOR_SLOT_ID_END = FireworkOrbCraftingTableBlockEntity.FADE_COLOR_SLOT_ID_END; //22
    public static final int OUTPUT_SLOT_ID = FireworkOrbCraftingTableBlockEntity.OUTPUT_SLOT_ID; //23
    public static final int INV_SLOT_START = 24;
    private static final int INV_SLOT_END = 51;
    private static final int USE_ROW_SLOT_START = 51;
    private static final int USE_ROW_SLOT_END = 60;

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
        super(ContainerTypeRegistry.FIREWORK_ORB_CRAFTING_TABLE.get(), pContainerId, pos, playerInventory, player);
        if (blockEntity != null) blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(this::addCraftingSlots);
        layoutPlayerInventorySlots(8, 140);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE.get());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot.hasItem()){
            ItemStack slotItem = slot.getItem();
            itemstack = slotItem.copy();
            if (pIndex == OUTPUT_SLOT_ID) {
                if (!this.moveItemStackTo(slotItem, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotItem, itemstack);
            }
            else if (pIndex > OUTPUT_SLOT_ID || pIndex < CORE_SLOT_ID){
                if (FireworkOrbCraftingTableBlockEntity.isFireworkOrbCore.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, CORE_SLOT_ID, CORE_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (FireworkOrbCraftingTableBlockEntity.isExplosionShapePattern.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, PATTERN_SLOT_ID, PATTERN_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (FireworkOrbCraftingTableBlockEntity.isFireworkMixture.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, FORCE_SLOT_ID, DAMAGE_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (FireworkOrbCraftingTableBlockEntity.isTrailEffectItem.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, TRAIL_SLOT_ID, TRAIL_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (FireworkOrbCraftingTableBlockEntity.isSparkleEffectItem.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, SPARKLE_SLOT_ID, SPARKLE_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (FireworkOrbCraftingTableBlockEntity.isColorItem.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, COLOR_SLOT_ID_START, FADE_COLOR_SLOT_ID_END + 1, false)) {
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

    protected void addCraftingSlots(IItemHandler handler){
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


}
