package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.ModMethod;
import net.jyu4.thebeetrootmod.block.blockentity.BlockEntityAltar;
import net.jyu4.thebeetrootmod.block.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockAltar extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;;

    protected static final VoxelShape X_SHAPE_ALTAR = Block.box(3.0, 3.0, 1.0, 13.0, 9.0, 15.0);;
    protected static final VoxelShape Z_SHAPE_ALTAR = Block.box(1.0, 3.0, 3.0, 15.0, 9.0, 13.0);;

    protected static final VoxelShape X_SHAPE_BASE = Block.box(2.0, 0.0, 0.0, 14.0, 3.0, 16.0);;
    protected static final VoxelShape Z_SHAPE_BASE = Block.box(0.0, 0.0, 2.0, 16.0, 3.0, 14.0);;

    protected static final VoxelShape X_SHAPE = Shapes.or(X_SHAPE_ALTAR, X_SHAPE_BASE);;
    protected static final VoxelShape Z_SHAPE = Shapes.or(Z_SHAPE_ALTAR, Z_SHAPE_BASE);;

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BlockAltar(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.valueOf(false)));
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            return InteractionResult.sidedSuccess(true);
        }

        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (!(be instanceof BlockEntityAltar)) {
            return InteractionResult.sidedSuccess(false);
        }

        BlockEntityAltar altar = (BlockEntityAltar) be;
        if (!altar.isOwner(pPlayer)) {
            ModMethod.displayMessage(pPlayer, "block.thebeetrootmod.altar.not_owned");
            pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(false);
        }

        NetworkHooks.openScreen((ServerPlayer) pPlayer, altar, pPos);
        return InteractionResult.sidedSuccess(false);
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {

        BlockEntity be = pLevel.getBlockEntity(pPos);

        if (be instanceof BlockEntityAltar && pPlacer instanceof Player) {
            BlockEntityAltar station = (BlockEntityAltar) be;
            station.setOwner((Player) pPlacer);
        }

        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
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

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            double d0 = (double)pPos.getX() + 0.5D;
            double d1 = (double)pPos.getY() + 0.85D;
            double d2 = (double)pPos.getZ() + 0.5D;
            if (pRandom.nextDouble() < 0.1D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = pRandom.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * -0.15D : d4;
            double d6 = pRandom.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * -0.15D : d4;
            pLevel.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.SMALL_FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlockEntityAltar(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.ALTAR_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1, pBlockEntity));
    }
}
