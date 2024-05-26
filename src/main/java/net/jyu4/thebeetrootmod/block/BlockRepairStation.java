package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.block.blockentity.BlockEntityRepairStation;
import net.jyu4.thebeetrootmod.block.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockRepairStation extends BlockBaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape X_SHAPE_TABLE = Block.box(2.0, 5.0, 0.0, 14.0, 8.0, 16.0);
    protected static final VoxelShape Z_SHAPE_TABLE = Block.box(0.0, 5.0, 2.0, 16.0, 8.0, 14.0);

    protected static final VoxelShape X_SHAPE_LEG_1 = Block.box(2.0, 0.0, 2.0, 4, 5.0, 5);
    protected static final VoxelShape X_SHAPE_LEG_2 = Block.box(2.0, 0.0, 11, 4, 5.0, 14);
    protected static final VoxelShape X_SHAPE_LEG_3 = Block.box(12, 0.0, 2.0, 14, 5.0, 5);
    protected static final VoxelShape X_SHAPE_LEG_4 = Block.box(12, 0.0, 11, 14, 5.0, 14);

    protected static final VoxelShape Z_SHAPE_LEG_1 = Block.box(2.0, 0.0, 2.0, 5.0, 5.0, 4.0);
    protected static final VoxelShape Z_SHAPE_LEG_2 = Block.box(2.0, 0.0, 12, 5.0, 5.0, 14);
    protected static final VoxelShape Z_SHAPE_LEG_3 = Block.box(11.0, 0.0, 2.0, 14.0, 5.0, 4.0);
    protected static final VoxelShape Z_SHAPE_LEG_4 = Block.box(11.0, 0.0, 12, 14.0, 5.0, 14);

    protected static final VoxelShape X_LEGS = Shapes.or(X_SHAPE_LEG_1, X_SHAPE_LEG_2,X_SHAPE_LEG_3,X_SHAPE_LEG_4);
    protected static final VoxelShape Z_LEGS = Shapes.or(Z_SHAPE_LEG_1, Z_SHAPE_LEG_2,Z_SHAPE_LEG_3,Z_SHAPE_LEG_4);
    protected static final VoxelShape X_SHAPE = Shapes.or(X_LEGS, X_SHAPE_TABLE);
    protected static final VoxelShape Z_SHAPE = Shapes.or(Z_LEGS, Z_SHAPE_TABLE);

    public BlockRepairStation(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch ((Direction) pState.getValue(FACING)) {
            case NORTH -> Z_SHAPE;
            case SOUTH -> Z_SHAPE;
            case EAST -> X_SHAPE;
            case WEST -> X_SHAPE;
            default -> Z_SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlockEntityRepairStation(pPos, pState);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            return InteractionResult.sidedSuccess(true);
        }

        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (!(be instanceof BlockEntityRepairStation)) {
            return InteractionResult.sidedSuccess(false);
        }

        BlockEntityRepairStation repairStation = (BlockEntityRepairStation) be;
        /*
        if (!repairStation.isOwner(pPlayer)) {
            ModUtils.displayMessage(pPlayer, "block.thebeetrootmod.altar.not_owned");
            pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(false);
        }

         */
        //ModUtils.displayMessage(pPlayer, "debug.beetrootmod");
        NetworkHooks.openScreen((ServerPlayer) pPlayer, repairStation, pPos);
        return InteractionResult.sidedSuccess(false);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof BlockEntityRepairStation) {
                ((BlockEntityRepairStation) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.REPAIR_STATION_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1, pBlockEntity));
    }
}
