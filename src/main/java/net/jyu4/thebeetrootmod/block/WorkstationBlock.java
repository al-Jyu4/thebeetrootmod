package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.gui.WorkstationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WorkstationBlock extends Block {
    private static final Component CONTAINER_TITLE = Component.translatable("thebeetrootmod.workstation_crafting");
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;;
    protected static final VoxelShape X_LEGS;
    protected static final VoxelShape Z_LEGS;
    protected static final VoxelShape X_SHAPE;
    protected static final VoxelShape Z_SHAPE;

    protected static final VoxelShape X_SHAPE_TABLE;
    protected static final VoxelShape Z_SHAPE_TABLE;

    protected static final VoxelShape X_SHAPE_LEG_1;
    protected static final VoxelShape X_SHAPE_LEG_2;
    protected static final VoxelShape X_SHAPE_LEG_3;
    protected static final VoxelShape X_SHAPE_LEG_4;

    protected static final VoxelShape Z_SHAPE_LEG_1;
    protected static final VoxelShape Z_SHAPE_LEG_2;
    protected static final VoxelShape Z_SHAPE_LEG_3;
    protected static final VoxelShape Z_SHAPE_LEG_4;

    public WorkstationBlock(Properties pProperties) {
        super(pProperties);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return (BlockState)this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            return InteractionResult.CONSUME;
        }
    }

    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((p_277304_, p_277305_, p_277306_) -> new WorkstationMenu(p_277304_, p_277305_, ContainerLevelAccess.create(pLevel, pPos)), CONTAINER_TITLE);
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    static {
        X_SHAPE_TABLE = Block.box(2.0, 5.0, 0.0, 14.0, 8.0, 16.0);
        Z_SHAPE_TABLE = Block.box(0.0, 5.0, 2.0, 16.0, 8.0, 14.0);

        X_SHAPE_LEG_1 = Block.box(2.0, 0.0, 2.0, 4, 5.0, 5);
        X_SHAPE_LEG_2 = Block.box(2.0, 0.0, 11, 4, 5.0, 14);
        X_SHAPE_LEG_3 = Block.box(12, 0.0, 2.0, 14, 5.0, 5);
        X_SHAPE_LEG_4 = Block.box(12, 0.0, 11, 14, 5.0, 14);

        Z_SHAPE_LEG_1 = Block.box(2.0, 0.0, 2.0, 5.0, 5.0, 4.0);
        Z_SHAPE_LEG_2 = Block.box(2.0, 0.0, 12, 5.0, 5.0, 14);
        Z_SHAPE_LEG_3 = Block.box(11.0, 0.0, 2.0, 14.0, 5.0, 4.0);
        Z_SHAPE_LEG_4 = Block.box(11.0, 0.0, 12, 14.0, 5.0, 14);

        X_LEGS = Shapes.or(X_SHAPE_LEG_1, X_SHAPE_LEG_2,X_SHAPE_LEG_3,X_SHAPE_LEG_4);
        Z_LEGS = Shapes.or(Z_SHAPE_LEG_1, Z_SHAPE_LEG_2,Z_SHAPE_LEG_3,Z_SHAPE_LEG_4);

        X_SHAPE = Shapes.or(X_LEGS, X_SHAPE_TABLE);
        Z_SHAPE = Shapes.or(Z_LEGS, Z_SHAPE_TABLE);
    }
}
