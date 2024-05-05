package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.gui.ShrineMenu;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockShrine extends Block {

    private static final Component CONTAINER_TITLE = Component.translatable("thebeetrootmod.workstation_crafting");

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape BASE1 = Block.box(0,0,0,16,2,16);
    protected static final VoxelShape BASE2 = box(1,2,1,15,6,15);
    protected static final VoxelShape SHAPE0 = Shapes.or(BASE1, BASE2);

    public BlockShrine(Properties pProperties) {
        super(pProperties);
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
        return new SimpleMenuProvider((p_277304_, p_277305_, p_277306_) -> new ShrineMenu(p_277304_, p_277305_, ContainerLevelAccess.create(pLevel, pPos)), CONTAINER_TITLE);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE0;
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
