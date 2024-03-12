package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkMissileCraftingTableBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkOrbCraftingTableBlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireworkMissileCraftingTableContainer extends AbstractFireworkContainer implements IFireworkCraftingTableContainer {

    public static final int HOMING_MODULE_SLOT_ID = FireworkMissileCraftingTableBlockEntity.HOMING_MODULE_SLOT_ID; //0
    public static final int FUSE_SLOT_ID = FireworkMissileCraftingTableBlockEntity.FUSE_SLOT_ID; //1
    public static final int STAR_SLOT_ID_START = FireworkMissileCraftingTableBlockEntity.STAR_SLOT_ID_START; //2
    public static final int STAR_SLOT_ID_END = FireworkMissileCraftingTableBlockEntity.STAR_SLOT_ID_END; //9
    public static final int SPEED_SLOT_ID = FireworkMissileCraftingTableBlockEntity.SPEED_SLOT_ID; //10
    public static final int FLIGHT_TIME_SLOT_ID = FireworkMissileCraftingTableBlockEntity.FLIGHT_TIME_SLOT_ID; //11
    public static final int SPARK_COLOR_SLOT_ID = FireworkMissileCraftingTableBlockEntity.SPARK_COLOR_SLOT_ID; //12
    public static final int WRAPPING_PAPER_SLOT_ID = FireworkMissileCraftingTableBlockEntity.WRAPPING_PAPER_SLOT_ID ; //13
    public static final int BASE_COLOR_SLOT_ID = FireworkMissileCraftingTableBlockEntity.BASE_COLOR_SLOT_ID; //14;
    public static final int PATTERN_COLOR_SLOT_ID = FireworkMissileCraftingTableBlockEntity.PATTERN_COLOR_SLOT_ID; //15;
    public static final int OUTPUT_SLOT_ID = FireworkMissileCraftingTableBlockEntity.OUTPUT_SLOT_ID; //16
    private static final int INV_SLOT_START = 17;
    private static final int INV_SLOT_END = 44;
    private static final int USE_ROW_SLOT_START = 44;
    private static final int USE_ROW_SLOT_END = 53;

    private ModuleSlot moduleSlot;
    private FuseSlot fuseSlot;
    private List<StarSlot> starSlotList = new ArrayList<>();
    private AmplifierSlot speedSlot;
    private AmplifierSlot flightTimeSlot;
    private DyeSlot sparkColorSlot;
    private WrappingMaterialSlot wrappingMaterialSlot;
    private DyeSlot baseColorSlot;
    private DyeSlot patternColorSlot;
    private OutputSlot outputSlot;

    public FireworkMissileCraftingTableContainer(int pContainerId, BlockPos pos, Inventory playerInventory, Player player) {
        super(ContainerTypeRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get(), pContainerId, pos, playerInventory, player);
        if (blockEntity != null) blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(this::addCraftingSlots);
        layoutPlayerInventorySlots(8, 140);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get());
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
            else if (pIndex > OUTPUT_SLOT_ID || pIndex < HOMING_MODULE_SLOT_ID){
                if (FireworkMissileCraftingTableBlockEntity.isHomingModule.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, HOMING_MODULE_SLOT_ID, HOMING_MODULE_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (FireworkMissileCraftingTableBlockEntity.isFuseItem.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, FUSE_SLOT_ID, FUSE_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (FireworkMissileCraftingTableBlockEntity.isOrbOrStar.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, STAR_SLOT_ID_START, STAR_SLOT_ID_END + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (FireworkMissileCraftingTableBlockEntity.isFireworkMixture.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, SPEED_SLOT_ID, FLIGHT_TIME_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (FireworkMissileCraftingTableBlockEntity.isWrappingPaper.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, WRAPPING_PAPER_SLOT_ID, WRAPPING_PAPER_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (FireworkMissileCraftingTableBlockEntity.isColorItem.test(slotItem)){
                    if (!this.moveItemStackTo(slotItem, SPARK_COLOR_SLOT_ID, PATTERN_COLOR_SLOT_ID + 1, false)) {
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

    @Override
    public void addCraftingSlots(IItemHandler handler) {
        this.moduleSlot = new ModuleSlot(handler, FireworkMissileCraftingTableBlockEntity.HOMING_MODULE_SLOT_ID, 8, 36);
        addSlot(moduleSlot);
        this.fuseSlot = new FuseSlot(handler, FireworkMissileCraftingTableBlockEntity.FUSE_SLOT_ID, 71, 36);
        addSlot(fuseSlot);
        addStarSlots(handler, 44, 18, 0);
        addStarSlots(handler, 44, 54, 4);
        this.speedSlot = new AmplifierSlot(handler, FireworkMissileCraftingTableBlockEntity.SPEED_SLOT_ID, 125, 18);
        addSlot(speedSlot);
        this.flightTimeSlot = new AmplifierSlot(handler, FireworkMissileCraftingTableBlockEntity.FLIGHT_TIME_SLOT_ID, 143, 18);
        addSlot(flightTimeSlot);
        this.sparkColorSlot = new DyeSlot(handler, FireworkMissileCraftingTableBlockEntity.SPARK_COLOR_SLOT_ID,134, 54);
        addSlot(sparkColorSlot);
        this.wrappingMaterialSlot = new WrappingMaterialSlot(handler, FireworkMissileCraftingTableBlockEntity.WRAPPING_PAPER_SLOT_ID, 98, 90);
        addSlot(wrappingMaterialSlot);
        this.baseColorSlot = new DyeSlot(handler, FireworkMissileCraftingTableBlockEntity.BASE_COLOR_SLOT_ID, 125, 90);
        addSlot(baseColorSlot);
        this.patternColorSlot = new DyeSlot(handler, FireworkMissileCraftingTableBlockEntity.PATTERN_COLOR_SLOT_ID, 143, 90);
        addSlot(patternColorSlot);
        this.outputSlot = new OutputSlot(handler, FireworkMissileCraftingTableBlockEntity.OUTPUT_SLOT_ID, 44, 104);
        addSlot(outputSlot);
    }

    private void addStarSlots(IItemHandler handler, int xo, int yo, int idDelta) {
        int startID = FireworkMissileCraftingTableBlockEntity.STAR_SLOT_ID_START + idDelta;
        for (int i = 0; i < 4; i++){
            StarSlot starSlot = new StarSlot(handler, startID + i, xo + i * 18, yo);
            addSlot(starSlot);
            starSlotList.add(starSlot);
        }
    }

    @Override
    public boolean hasValidItemsForCrafting() {
        return this.slots.stream().allMatch(Objects::nonNull)
                && this.fuseSlot.hasItem()
                && this.starSlotList.stream().anyMatch(Slot::hasItem)
                && this.speedSlot.hasItem()
                && this.flightTimeSlot.hasItem()
                && this.wrappingMaterialSlot.hasItem();
    }
}
