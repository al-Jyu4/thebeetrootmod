package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.gui.BlockEntityContainerProvider;
import net.jyu4.thebeetrootmod.gui.ContainerResearchTable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class BlockResearchTable extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BlockResearchTable(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (!pPlayer.isShiftKeyDown()) {
            if (pPlayer instanceof ServerPlayer) {
                BlockEntityContainerProvider.openGui((ServerPlayer) pPlayer, station, (i, playerInventory, playerEntity) -> new ContainerResearchTable(i, station, playerInventory));
            }
            return InteractionResult.SUCCESS;
        } /*else if (station.isOwner(pPlayer)) {
            if (pPlayer instanceof ServerPlayer) {
                BlockEntityContainerProvider.openGui((ServerPlayer) pPlayer, station, (i, playerInventory, playerEntity) -> new ContainerGasStationAdmin(i, station, playerInventory));
            }
            return InteractionResult.SUCCESS;
        }
        */
        return InteractionResult.FAIL;
    }
}
