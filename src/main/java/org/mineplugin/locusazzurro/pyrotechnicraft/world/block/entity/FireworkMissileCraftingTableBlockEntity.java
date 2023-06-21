package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockEntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkWrapper;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.FireworkFuse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class FireworkMissileCraftingTableBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public static final int SLOT_COUNT = 17;
    public static final int HOMING_MODULE_SLOT_ID = 0;
    public static final int FUSE_SLOT_ID = 1;
    public static final int STAR_SLOT_ID_START = 2;
    public static final int STAR_SLOT_ID_END = 9;
    public static final int STAR_SLOT_COUNT = 8;
    public static final int SPEED_SLOT_ID = 10;
    public static final int FLIGHT_TIME_SLOT_ID = 11;
    public static final int SPARK_COLOR_SLOT_ID = 12;
    public static final int WRAPPING_PAPER_SLOT_ID = 13;
    public static final int BASE_COLOR_SLOT_ID = 14;
    public static final int PATTERN_COLOR_SLOT_ID = 15;
    public static final int OUTPUT_SLOT_ID = 16;
    public static Predicate<ItemStack> isHomingModule = i -> i.is(ItemRegistry.FIREWORK_HOMING_MODULE.get());
    public static Predicate<ItemStack> isFuseItem = i -> i.getItem() instanceof FireworkFuse;
    public static Predicate<ItemStack> isFireworkOrb = i -> i.is(ItemRegistry.FIREWORK_ORB.get());
    public static Predicate<ItemStack> isFireworkStar = i -> i.is(Items.FIREWORK_STAR);
    public static Predicate<ItemStack> isOrbOrStar = isFireworkOrb.or(isFireworkStar);
    public static Predicate<ItemStack> isFireworkMixture = i -> i.is(ItemRegistry.FIREWORK_MIXTURE.get());
    public static Predicate<ItemStack> isColorItem = i -> i.getItem() instanceof DyeItem;
    public static Predicate<ItemStack> isWrappingPaper = i -> i.is(ItemRegistry.FIREWORK_WRAPPING_PAPER.get());
    public static Predicate<ItemStack> isFireworkMissile = i -> i.is(ItemRegistry.FIREWORK_MISSILE.get());
    public FireworkMissileCraftingTableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.FIREWORK_MISSILE_CRAFTING_TABLE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == HOMING_MODULE_SLOT_ID) return isHomingModule.test(stack);
                if (slot == FUSE_SLOT_ID) return isFuseItem.test(stack);
                if (slot >= STAR_SLOT_ID_START && slot <= STAR_SLOT_ID_END) return isOrbOrStar.test(stack);
                if (slot == SPEED_SLOT_ID || slot == FLIGHT_TIME_SLOT_ID) return isFireworkMixture.test(stack);
                if (slot == SPARK_COLOR_SLOT_ID || slot == BASE_COLOR_SLOT_ID || slot == PATTERN_COLOR_SLOT_ID)
                    return isColorItem.test(stack);
                if (slot == WRAPPING_PAPER_SLOT_ID) return isWrappingPaper.test(stack);
                if (slot == OUTPUT_SLOT_ID) return isFireworkMissile.test(stack);
                return false;
            }

            @NotNull
            @Override
            public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void craftFireworkMissile() {
        handler.ifPresent(handler -> {
            CompoundTag itemTag = new CompoundTag();
            ListTag payloadList = new ListTag();
            ItemStack result = new ItemStack(ItemRegistry.FIREWORK_MISSILE.get());

            ItemStack homingModuleItem = handler.getStackInSlot(HOMING_MODULE_SLOT_ID);
            if (isHomingModule.test(homingModuleItem)){
                itemTag.putBoolean("Homing", true);
                handler.extractItem(HOMING_MODULE_SLOT_ID, 1, false);
            }

            ItemStack fuseItem = handler.getStackInSlot(FUSE_SLOT_ID);
            if (fuseItem.getItem() instanceof FireworkFuse fuse){
                FireworkFuse.FuseType type = fuse.getType();
                if (type != FireworkFuse.FuseType.CUSTOM){
                    itemTag.putInt("FuseDelay", type.getFuseDelay());
                }
                else {
                    itemTag.putInt("FuseDelay", fuseItem.getOrCreateTag().getInt("FuseDelay"));
                }
                handler.extractItem(FUSE_SLOT_ID, 1, false);
            }

            for (int i = 0; i < STAR_SLOT_COUNT; i++){
                int curSlot = STAR_SLOT_ID_START + i;
                ItemStack starItem = handler.getStackInSlot(curSlot);
                if (isOrbOrStar.test(starItem)){
                    payloadList.add(FireworkWrapper.wrapSingleFireworkExplosion(starItem));
                    handler.extractItem(curSlot, 1, false);
                }
            }

            ItemStack speedItem = handler.getStackInSlot(SPEED_SLOT_ID);
            if (isFireworkMixture.test(speedItem)){
                itemTag.putDouble("Speed", speedItem.getCount() * 0.2d);
                handler.extractItem(SPEED_SLOT_ID, speedItem.getCount(), false);
            }

            ItemStack flightTimeItem = handler.getStackInSlot(FLIGHT_TIME_SLOT_ID);
            if (isFireworkMixture.test(flightTimeItem)){
                itemTag.putInt("FlightTime", 5 + flightTimeItem.getCount() * 5);
                handler.extractItem(FLIGHT_TIME_SLOT_ID, flightTimeItem.getCount(), false);
            }

            ItemStack sparkColorItem = handler.getStackInSlot(SPARK_COLOR_SLOT_ID);
            if (sparkColorItem.getItem() instanceof DyeItem dye){
                itemTag.putInt("SparkColor", dye.getDyeColor().getFireworkColor());
                handler.extractItem(SPARK_COLOR_SLOT_ID, 1, false);
            }

            ItemStack wrappingPaperItem = handler.getStackInSlot(WRAPPING_PAPER_SLOT_ID);
            if (isWrappingPaper.test(wrappingPaperItem)) {
                handler.extractItem(WRAPPING_PAPER_SLOT_ID, 1, false);

                ItemStack baseColorItem = handler.getStackInSlot(BASE_COLOR_SLOT_ID);
                if (baseColorItem.getItem() instanceof DyeItem dye) {
                    itemTag.putInt("BaseColor", dye.getDyeColor().getFireworkColor()); //TODO float texture diffuse color processor
                    handler.extractItem(BASE_COLOR_SLOT_ID, 1, false);
                }

                ItemStack patternColorItem = handler.getStackInSlot(PATTERN_COLOR_SLOT_ID);
                if (patternColorItem.getItem() instanceof DyeItem dye) {
                    itemTag.putInt("PatternColor", dye.getDyeColor().getFireworkColor());
                    handler.extractItem(PATTERN_COLOR_SLOT_ID, 1, false);
                }
            }
            if (!payloadList.isEmpty()){
                itemTag.put("PayloadList", payloadList);
            }

            result.setTag(itemTag);
            handler.insertItem(OUTPUT_SLOT_ID, result, false);
            this.setChanged();
        });
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag.contains("Inventory")) {
            itemHandler.deserializeNBT(pTag.getCompound("Inventory"));
        }
        super.load(pTag);
    }

    @Override
    public void saveAdditional(CompoundTag pTag) {
        pTag.put("Inventory", itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }
}
