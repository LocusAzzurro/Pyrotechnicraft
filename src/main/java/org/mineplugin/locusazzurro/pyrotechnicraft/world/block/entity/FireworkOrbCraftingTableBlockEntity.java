package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockEntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ModItemTags;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkOrbCraftingTableContainer;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.shape.ExplosionShape;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.ExplosionShapePattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FireworkOrbCraftingTableBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public static final int SLOT_COUNT = 24;
    public static final int CORE_SLOT_ID = 0;
    public static final int PATTERN_SLOT_ID = 1;
    public static final int FORCE_SLOT_ID = 2;
    public static final int SPARK_SLOT_ID = 3;
    public static final int DAMAGE_SLOT_ID = 4;
    public static final int TRAIL_SLOT_ID = 5;
    public static final int SPARKLE_SLOT_ID = 6;
    public static final int COLOR_SLOT_ID_START = 7;
    public static final int COLOR_SLOT_ID_END = 14;
    public static final int COLOR_SLOT_COUNT = 8;
    public static final int FADE_COLOR_SLOT_ID_START = 15;
    public static final int FADE_COLOR_SLOT_ID_END = 22;
    public static final int FADE_COLOR_SLOT_COUNT = 8;
    public static final int OUTPUT_SLOT_ID = 23;
    private final RangedWrapper COLOR_ITEMS = new RangedWrapper(itemHandler, COLOR_SLOT_ID_START, COLOR_SLOT_ID_END + 1);
    private final RangedWrapper FADE_COLOR_ITEMS = new RangedWrapper(itemHandler, FADE_COLOR_SLOT_ID_START, FADE_COLOR_SLOT_ID_END + 1);
    public static Predicate<ItemStack> isFireworkOrbCore = i -> i.is(ItemRegistry.FIREWORK_ORB_CORE.get());
    public static Predicate<ItemStack> isExplosionShapePattern = i -> i.getItem() instanceof ExplosionShapePattern;
    public static Predicate<ItemStack> isFireworkMixture = i -> i.is(ItemRegistry.FIREWORK_MIXTURE.get());
    public static Predicate<ItemStack> isTrailEffectItem = i -> i.is(ModItemTags.TRAIL_EFFECT_ITEMS);
    public static Predicate<ItemStack> isSparkleEffectItem = i -> i.is(ModItemTags.SPARKLE_EFFECT_ITEMS);
    public static Predicate<ItemStack> isColorItem = i -> i.getItem() instanceof DyeItem;
    public static Predicate<ItemStack> isFireworkOrb = i -> i.is(ItemRegistry.FIREWORK_ORB.get());
    public FireworkOrbCraftingTableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.FIREWORK_ORB_CRAFTING_TABLE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == CORE_SLOT_ID) return isFireworkOrbCore.test(stack);
                if (slot == PATTERN_SLOT_ID) return isExplosionShapePattern.test(stack);
                if (slot == FORCE_SLOT_ID || slot == SPARK_SLOT_ID || slot == DAMAGE_SLOT_ID)
                    return isFireworkMixture.test(stack);
                if (slot == TRAIL_SLOT_ID) return isTrailEffectItem.test(stack);
                if (slot == SPARKLE_SLOT_ID) return isSparkleEffectItem.test(stack);
                if (COLOR_SLOT_ID_START <= slot && slot <= FADE_COLOR_SLOT_ID_END) return isColorItem.test(stack);
                if (slot == OUTPUT_SLOT_ID) return isFireworkOrb.test(stack);
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
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void craftFireworkOrb() {
        handler.ifPresent(handler -> {
            CompoundTag itemTag = new CompoundTag();
            ItemStack result = new ItemStack(ItemRegistry.FIREWORK_ORB.get());
            ExplosionShape shape = ExplosionShape.SPHERE;

            handler.extractItem(CORE_SLOT_ID, 1, false);

            ItemStack patternItem = handler.getStackInSlot(PATTERN_SLOT_ID);
            if (patternItem.getItem() instanceof ExplosionShapePattern pattern) shape = pattern.getShape();
            itemTag.putString("Shape", shape.getName());

            ItemStack forceItem = handler.getStackInSlot(FORCE_SLOT_ID);
            if (isFireworkMixture.test(forceItem)){
                itemTag.putFloat("Force", 0.2f * forceItem.getCount());
                handler.extractItem(FORCE_SLOT_ID, forceItem.getCount(), false);
            }

            ItemStack sparkItem = handler.getStackInSlot(SPARK_SLOT_ID);
            if (isFireworkMixture.test(sparkItem)){
                itemTag.putInt("Sparks", sparkItem.getCount() * shape.getFireworkMixtureValue());
                if (shape.hasCostByDefault()) {
                    handler.extractItem(SPARK_SLOT_ID, FireworkOrbCraftingTableContainer.getPatternCost(patternItem), false);
                }
                else handler.extractItem(SPARK_SLOT_ID, sparkItem.getCount(), false);
            }

            ItemStack damageItem = handler.getStackInSlot(DAMAGE_SLOT_ID);
            if (isFireworkMixture.test(damageItem)){
                itemTag.putDouble("Damage", 1.0d * forceItem.getCount());
                handler.extractItem(DAMAGE_SLOT_ID, damageItem.getCount(), false);
            }

            ItemStack trailEffectItem = handler.getStackInSlot(TRAIL_SLOT_ID);
            if (!trailEffectItem.isEmpty() && isTrailEffectItem.test(trailEffectItem)){
                itemTag.putBoolean("Trail", true);
                handler.extractItem(TRAIL_SLOT_ID, 1 , false);
            }

            ItemStack sparkleEffectItem = handler.getStackInSlot(SPARKLE_SLOT_ID);
            if (!sparkleEffectItem.isEmpty() && isSparkleEffectItem.test(sparkleEffectItem)){
                itemTag.putBoolean("Sparkle", true);
                handler.extractItem(SPARKLE_SLOT_ID, 1 , false);
            }

            List<ItemStack> colorItems = new ArrayList<>();

            for (int i = 0; i < COLOR_SLOT_COUNT; i++){
                ItemStack colorItem = handler.getStackInSlot(COLOR_SLOT_ID_START + i);
                if (isColorItem.test(colorItem)){
                    colorItems.add(colorItem);
                    handler.extractItem(COLOR_SLOT_ID_START + i, 1 , false);
                }
            }

            if (colorItems.isEmpty()) colorItems.add(new ItemStack(Items.WHITE_DYE));

            int[] colors = colorItems.stream().flatMapToInt(i -> {
                if (i.getItem() instanceof DyeItem dye){
                    return IntStream.of(dye.getDyeColor().getFireworkColor());
                }
                else return IntStream.of(0xffffff);
            }).toArray();

            itemTag.putIntArray("Colors", colors);

            List<ItemStack> fadeColorItems = new ArrayList<>();

            for (int j = 0; j < FADE_COLOR_SLOT_COUNT; j++){
                ItemStack fadeColorItem = handler.getStackInSlot(FADE_COLOR_SLOT_ID_START + j);
                if (isColorItem.test(fadeColorItem)){
                    fadeColorItems.add(fadeColorItem);
                    handler.extractItem(FADE_COLOR_SLOT_ID_START + j, 1 , false);
                }
            }

            if (!fadeColorItems.isEmpty()){
                int[] fadeColors = fadeColorItems.stream().flatMapToInt(i -> {
                    if (i.getItem() instanceof DyeItem dye){
                        return IntStream.of(dye.getDyeColor().getFireworkColor());
                    }
                    else return IntStream.of(0xffffff);
                }).toArray();
                itemTag.putIntArray("FadeColors", fadeColors);
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
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("Inventory", itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }
}
