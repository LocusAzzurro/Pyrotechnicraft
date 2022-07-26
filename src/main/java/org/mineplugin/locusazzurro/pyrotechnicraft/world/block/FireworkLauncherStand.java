package org.mineplugin.locusazzurro.pyrotechnicraft.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkLauncherStandContainer;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container.FireworkMissileCraftingTableContainer;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkLauncherStandBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.block.entity.FireworkMissileCraftingTableBlockEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.data.FireworkWrapper;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FirecrackerEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.entity.FireworkMissileEntity;
import org.mineplugin.locusazzurro.pyrotechnicraft.world.item.Firecracker;

import java.util.Optional;

public class FireworkLauncherStand extends BaseEntityBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public FireworkLauncherStand() {
        super(BlockBehaviour.Properties.of(Material.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.FALSE));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FireworkLauncherStandBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof FireworkLauncherStandBlockEntity launcher) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    @NotNull
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen." + Pyrotechnicraft.MOD_ID + ".firework_launcher_stand");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new FireworkLauncherStandContainer(windowId, pPos, playerInventory, playerEntity, launcher.containerData);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) pPlayer, containerProvider, be.getBlockPos());
            } else {
                throw new IllegalStateException("Container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        boolean flag = pLevel.hasNeighborSignal(pPos);
        if (flag != pState.getValue(POWERED)) {
            if (flag && pLevel.getBlockEntity(pPos) instanceof FireworkLauncherStandBlockEntity launcher) {
                ItemStack firework = launcher.getFirework();
                if (!firework.isEmpty()){
                    FireworkMissileEntity fireworkEntity;
                    Vec3 pos = new Vec3(pPos.getX() + 0.5, pPos.getY() + 1.5, pPos.getZ() + 0.5);
                    if (FireworkLauncherStandBlockEntity.isFireworkRocket.test(firework)) {
                        fireworkEntity = new FireworkMissileEntity(pLevel, pos, FireworkWrapper.convertVanillaFireworkRocket(firework));
                    } else {
                        fireworkEntity = new FireworkMissileEntity(pLevel, pos, firework.getOrCreateTag());
                    }
                    int rotation = launcher.containerData.get(0);
                    int angle = launcher.containerData.get(1);
                    fireworkEntity.shootFromRotation(fireworkEntity, angle, rotation, 0, 1, 0f);
                    pLevel.addFreshEntity(fireworkEntity);
                }
            }
            pLevel.setBlock(pPos, pState.setValue(POWERED, flag), 3);
        }
    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED);
    }

}
