package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.BlockEntityTypeRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class FireworkLauncherStandBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public static final int SLOT_COUNT = 9;
    public static final int SLOT_ID_START = 0;
    public static final int SLOT_ID_END = 8;
    public static final int ROTATION_DATA_SLOT_ID = 0;
    public static final int ANGLE_DATA_SLOT_ID = 1;
    private int rotation;
    private int angle;
    public static Predicate<ItemStack> isFireworkMissile = i -> i.is(ItemRegistry.FIREWORK_MISSILE.get());
    public static Predicate<ItemStack> isFireworkRocket = i -> i.is(Items.FIREWORK_ROCKET);
    public static Predicate<ItemStack> isMissileOrRocket = isFireworkMissile.or(isFireworkRocket);

    public final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case ROTATION_DATA_SLOT_ID -> FireworkLauncherStandBlockEntity.this.rotation;
                case ANGLE_DATA_SLOT_ID -> FireworkLauncherStandBlockEntity.this.angle;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex){
                case ROTATION_DATA_SLOT_ID -> FireworkLauncherStandBlockEntity.this.rotation = pValue;
                case ANGLE_DATA_SLOT_ID -> FireworkLauncherStandBlockEntity.this.angle = pValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public FireworkLauncherStandBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.FIREWORK_LAUNCHER_STAND_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
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
        this.containerData.set(slot, value);
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
        pTag.putInt("Rotation", this.rotation);
        pTag.putInt("Angle", this.angle);
        super.load(pTag);
    }

    @Override
    public void saveAdditional(CompoundTag pTag) {
        pTag.put("Inventory", itemHandler.serializeNBT());
        this.rotation = pTag.getInt("Rotation");
        this.angle = pTag.getInt("Angle");
        super.saveAdditional(pTag);
    }
}
