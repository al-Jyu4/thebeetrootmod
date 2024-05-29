package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.block.blockentity.BlockEntityAltar;
import net.jyu4.thebeetrootmod.block.blockentity.ModBlockEntities;
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

public class BlockAltar extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape X_SHAPE_ALTAR = Block.box(3.0, 3.0, 1.0, 13.0, 9.0, 15.0);
    protected static final VoxelShape Z_SHAPE_ALTAR = Block.box(1.0, 3.0, 3.0, 15.0, 9.0, 13.0);

    protected static final VoxelShape X_SHAPE_BASE = Block.box(2.0, 0.0, 0.0, 14.0, 3.0, 16.0);
    protected static final VoxelShape Z_SHAPE_BASE = Block.box(0.0, 0.0, 2.0, 16.0, 3.0, 14.0);

    protected static final VoxelShape X_SHAPE = Shapes.or(X_SHAPE_ALTAR, X_SHAPE_BASE);
    protected static final VoxelShape Z_SHAPE = Shapes.or(Z_SHAPE_ALTAR, Z_SHAPE_BASE);

    public BlockAltar(Properties pProperties) {
        super(pProperties);
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlockEntityAltar(pPos, pState);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ALTAR_BE.get(), pLevel.isClientSide ? BlockEntityAltar::clientTick : BlockEntityAltar::serverTick);
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
