package net.jyu4.thebeetrootmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RitualRingBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape BASE = Block.box(1,0,1,15,5,15);
    protected static final VoxelShape INSIDE = box(4.0D, 0.1D, 4.0D, 12.0D, 5.0D, 12.0D);
    protected static final VoxelShape FRONT0 = box(4.0D, 4.0D, 1.0D, 12.0D, 5.0D, 4.0D);
    protected static final VoxelShape FRONT1 = box(4.0D, 4.0D, 12.0D, 12.0D, 5.0D, 15.0D);
    protected static final VoxelShape FRONT2 = box(12.0D, 4.0D, 4.0D, 15.0D, 5.0D, 12.0D);
    protected static final VoxelShape FRONT3 = box(1.0D, 4.0D, 4.0D, 4.0D, 5.0D, 12.0D);
    protected static final VoxelShape SHAPE0 = Shapes.join(BASE, Shapes.or(INSIDE, FRONT0), BooleanOp.ONLY_FIRST);
    protected static final VoxelShape SHAPE1 = Shapes.join(BASE, Shapes.or(INSIDE, FRONT1), BooleanOp.ONLY_FIRST);
    protected static final VoxelShape SHAPE2 = Shapes.join(BASE, Shapes.or(INSIDE, FRONT2), BooleanOp.ONLY_FIRST);
    protected static final VoxelShape SHAPE3 = Shapes.join(BASE, Shapes.or(INSIDE, FRONT3), BooleanOp.ONLY_FIRST);

    public RitualRingBlock(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch ((Direction) pState.getValue(FACING)) {
            case NORTH -> SHAPE0;
            case SOUTH -> SHAPE1;
            case EAST -> SHAPE2;
            case WEST -> SHAPE3;
            default -> SHAPE0;
        };
    }

    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return BASE;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return (BlockState)this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}
