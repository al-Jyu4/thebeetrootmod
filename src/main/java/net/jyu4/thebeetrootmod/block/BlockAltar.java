package net.jyu4.thebeetrootmod.block;

import net.jyu4.thebeetrootmod.block.blockentity.BlockEntityAltar;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockAltar extends BlockBase implements EntityBlock {
    public BlockAltar(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlockEntityAltar(pPos, pState);
    }
}
