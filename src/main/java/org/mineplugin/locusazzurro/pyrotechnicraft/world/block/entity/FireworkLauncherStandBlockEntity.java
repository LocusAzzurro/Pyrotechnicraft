package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
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
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkLauncherStandContainerData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class FireworkLauncherStandBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public static final int SLOT_COUNT = 9;
    public static final int SLOT_ID_START = 0;
    public static final int SLOT_ID_END = 8;
    public static final int ROTATION_DATA_SLOT_ID = 0;
    public static final int ANGLE_DATA_SLOT_ID = 1;
    public static Predicate<ItemStack> isFireworkMissile = i -> i.is(ItemRegistry.FIREWORK_MISSILE.get());
    public static Predicate<ItemStack> isFireworkRocket = i -> i.is(Items.FIREWORK_ROCKET);
    public static Predicate<ItemStack> isMissileOrRocket = isFireworkMissile.or(isFireworkRocket);
    public final FireworkLauncherStandContainerData containerData = new FireworkLauncherStandContainerData();

    public FireworkLauncherStandBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.FIREWORK_LAUNCHER_STAND_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        containerData.set(1, -90);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return (slot >= SLOT_ID_START && slot <= SLOT_ID_END && isMissileOrRocket.test(stack));
            }

            @NotNull
            @Override
            public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    public void setData(byte slot, short value){
        if (slot == ROTATION_DATA_SLOT_ID){
            this.containerData.set(ROTATION_DATA_SLOT_ID, Mth.clamp(this.containerData.get(ROTATION_DATA_SLOT_ID) + value, -180, 180));
        }
        if (slot == ANGLE_DATA_SLOT_ID){
            this.containerData.set(ANGLE_DATA_SLOT_ID, Mth.clamp(this.containerData.get(ANGLE_DATA_SLOT_ID) + value, -90, 0));
        }
        this.setChanged();
    }

    public ItemStack getFirework(){
        AtomicReference<ItemStack> firework = new AtomicReference<>(ItemStack.EMPTY);
        handler.ifPresent(handler -> {
            for (int i = 0; i < handler.getSlots(); i++){
                ItemStack item = handler.getStackInSlot(i);
                if (isMissileOrRocket.test(item)){
                    handler.extractItem(i, 1, false);
                    firework.set(item);
                    break;
                }
            }
        });
        return firework.get();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
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
        this.containerData.set(0, pTag.getInt("Rotation"));
        this.containerData.set(1, pTag.getInt("Angle"));
        super.load(pTag);
    }

    @Override
    public void saveAdditional(CompoundTag pTag) {
        pTag.put("Inventory", itemHandler.serializeNBT());
        pTag.putInt("Rotation", this.containerData.get(0));
        pTag.putInt("Angle", this.containerData.get(1));
        super.saveAdditional(pTag);
    }

}
