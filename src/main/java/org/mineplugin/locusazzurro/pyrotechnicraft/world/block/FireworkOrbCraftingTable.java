package org.mineplugin.locusazzurro.pyrotechnicraft.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkOrbCraftingTableContainer;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkOrbCraftingTableBlockEntity;

public class FireworkOrbCraftingTable extends Block implements EntityBlock {

    public FireworkOrbCraftingTable() {
        super(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1.5f));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FireworkOrbCraftingTableBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof FireworkOrbCraftingTableBlockEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    @NotNull
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen." + Pyrotechnicraft.MOD_ID + ".firework_orb_crafting_table");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new FireworkOrbCraftingTableContainer(windowId, pPos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) pPlayer, containerProvider, be.getBlockPos());
            } else {
                throw new IllegalStateException("Container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }


}

