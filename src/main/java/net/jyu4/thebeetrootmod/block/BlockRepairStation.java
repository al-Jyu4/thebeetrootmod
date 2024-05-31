package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.blockentity.BlockEntityRepairStation;
import net.jyu4.thebeetrootmod.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockRepairStation extends BlockBaseEntity {

    ///------------------------------///
    // shape

    protected static final VoxelShape TABLE0 = Block.box(2.0, 5.0, 0.0, 14.0, 8.0, 16.0);
    protected static final VoxelShape TABLE1 = Block.box(0.0, 5.0, 2.0, 16.0, 8.0, 14.0);

    protected static final VoxelShape LEG00 = Block.box(2.0, 0.0, 2.0, 4, 5.0, 5);
    protected static final VoxelShape LEG01 = Block.box(2.0, 0.0, 11, 4, 5.0, 14);
    protected static final VoxelShape LEG02 = Block.box(12, 0.0, 2.0, 14, 5.0, 5);
    protected static final VoxelShape LEG03 = Block.box(12, 0.0, 11, 14, 5.0, 14);

    protected static final VoxelShape LEG10 = Block.box(2.0, 0.0, 2.0, 5.0, 5.0, 4.0);
    protected static final VoxelShape LEG11 = Block.box(2.0, 0.0, 12, 5.0, 5.0, 14);
    protected static final VoxelShape LEG12 = Block.box(11.0, 0.0, 2.0, 14.0, 5.0, 4.0);
    protected static final VoxelShape LEG13 = Block.box(11.0, 0.0, 12, 14.0, 5.0, 14);

    protected static final VoxelShape LEGS0 = Shapes.or(LEG00, LEG01, LEG02, LEG03);
    protected static final VoxelShape LEGS1 = Shapes.or(LEG10, LEG11, LEG12, LEG13);
    protected static final VoxelShape SHAPE0 = Shapes.or(LEGS0, TABLE0);
    protected static final VoxelShape SHAPE1 = Shapes.or(LEGS1, TABLE1);

    ///------------------------------///
    // base

    public BlockRepairStation(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch ((Direction) pState.getValue(FACING)) {
            case NORTH, SOUTH -> SHAPE1;
            case EAST, WEST -> SHAPE0;
            default -> SHAPE1;
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlockEntityRepairStation(pPos, pState);
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
        return createTickerHelper(pBlockEntityType, ModBlockEntities.REPAIR_STATION_BE.get(),(pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1, pBlockEntity));
    }

    ///------------------------------///
    // functionalities

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            return InteractionResult.sidedSuccess(true);
        }

        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (!(be instanceof BlockEntityRepairStation)) {
            return InteractionResult.sidedSuccess(false);
        }

        BlockEntityRepairStation repairStation = (BlockEntityRepairStation) be;
        NetworkHooks.openScreen((ServerPlayer) pPlayer, repairStation, pPos);
        return InteractionResult.sidedSuccess(false);
    }
}
