package net.jyu4.thebeetrootmod.block;

import net.minecraft.core.BlockPos;
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

public class BlockWell extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    //protected static final VoxelShape BASE = Block.box(0,0,0,16,2,16);
    protected static final VoxelShape WELL = box(1,0,1,15,7,15);
    protected static final VoxelShape INSIDE = box(4.0D, 1.0D, 4.0D, 12.0D, 7.0D, 12.0D);
    //protected static final VoxelShape SHAPE0 = Shapes.or(BASE, Shapes.join(WELL, INSIDE, BooleanOp.ONLY_FIRST));
    protected static final VoxelShape SHAPE0 = Shapes.join(WELL, INSIDE, BooleanOp.ONLY_FIRST);

    public BlockWell(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE0;
    }

    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return INSIDE;
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
