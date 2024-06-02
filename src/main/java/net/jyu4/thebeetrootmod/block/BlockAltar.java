package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.blockentity.BlockEntityAltar;
import net.jyu4.thebeetrootmod.blockentity.BlockEntityRepairStation;
import net.jyu4.thebeetrootmod.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockAltar extends BlockBaseEntity {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape ALTAR0 = Block.box(3.0, 3.0, 1.0, 13.0, 9.0, 15.0);
    protected static final VoxelShape ALTAR1 = Block.box(1.0, 3.0, 3.0, 15.0, 9.0, 13.0);

    protected static final VoxelShape BASE0 = Block.box(2.0, 0.0, 0.0, 14.0, 3.0, 16.0);
    protected static final VoxelShape BASE1 = Block.box(0.0, 0.0, 2.0, 16.0, 3.0, 14.0);

    protected static final VoxelShape SHAPE0 = Shapes.or(ALTAR0, BASE0);
    protected static final VoxelShape SHAPE1 = Shapes.or(ALTAR1, BASE1);

    public BlockAltar(Properties pProperties) {
        super(pProperties);
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlockEntityAltar(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof BlockEntityAltar) {
                ((BlockEntityAltar) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ALTAR_BE.get(), pLevel.isClientSide ? BlockEntityAltar::clientTick : BlockEntityAltar::serverTick);
    }


    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch ((Direction) pState.getValue(FACING)) {
            case NORTH, SOUTH -> SHAPE1;
            case EAST, WEST -> SHAPE0;
            default -> SHAPE1;
        };
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return (BlockState) this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
