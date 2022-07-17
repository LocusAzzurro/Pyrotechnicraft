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

public class FireworkOrbCraftingTableContainer extends AbstractFireworkCraftingTableContainer {
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
        if (blockEntity != null) blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(this::addCraftingSlots);
        layoutPlayerInventorySlots(8, 140);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockRegistry.FIREWORK_ORB_CRAFTING_TABLE.get());
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
