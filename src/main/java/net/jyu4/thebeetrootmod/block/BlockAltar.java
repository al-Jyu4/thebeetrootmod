package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.block.blockentity.BlockEntityAltar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockAltar extends BlockBase implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape ALTAR0 = Block.box(3.0, 3.0, 1.0, 13.0, 9.0, 15.0);
    protected static final VoxelShape ALTAR1 = Block.box(1.0, 3.0, 3.0, 15.0, 9.0, 13.0);

    protected static final VoxelShape BASE0 = Block.box(2.0, 0.0, 0.0, 14.0, 3.0, 16.0);
    protected static final VoxelShape BASE1 = Block.box(0.0, 0.0, 2.0, 16.0, 3.0, 14.0);

    protected static final VoxelShape SHAPE0 = Shapes.or(ALTAR0, BASE0);
    protected static final VoxelShape SHAPE1 = Shapes.or(ALTAR1, BASE1);

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BlockAltar(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch ((Direction) pState.getValue(FACING)) {
            case NORTH -> SHAPE1;
            case SOUTH -> SHAPE1;
            case EAST -> SHAPE0;
            case WEST -> SHAPE0;
            default -> SHAPE1;
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlockEntityAltar(pPos, pState);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }
}
