package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ContainerTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkMissileCraftingTableBlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireworkMissileCraftingTableContainer extends AbstractFireworkCraftingTableContainer {

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
        if (blockEntity != null) blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(this::addCraftingSlots);
        layoutPlayerInventorySlots(8, 140);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, BlockRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get());
    }

    @Override
    protected void addCraftingSlots(IItemHandler handler) {
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
        this.wrappingMaterialSlot = new WrappingMaterialSlot(handler, FireworkMissileCraftingTableBlockEntity.WRAPPING_PAPER_SLOT, 98, 90);
        addSlot(wrappingMaterialSlot);
        this.baseColorSlot = new DyeSlot(handler, FireworkMissileCraftingTableBlockEntity.BASE_COLOR_SLOT, 125, 90);
        addSlot(baseColorSlot);
        this.patternColorSlot = new DyeSlot(handler, FireworkMissileCraftingTableBlockEntity.PATTERN_COLOR_SLOT, 143, 90);
        addSlot(patternColorSlot);
        this.outputSlot = new OutputSlot(handler, FireworkMissileCraftingTableBlockEntity.OUTPUT_SLOT, 44, 104);
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
