package net.jyu4.thebeetrootmod.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityResearchTable extends BlockEntityBase {

    private int progress = 0;
    private int maxProgress = 78;

    public BlockEntityResearchTable(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public final ContainerData FIELDS = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> BlockEntityResearchTable.this.progress;
                case 1 -> BlockEntityResearchTable.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> BlockEntityResearchTable.this.progress = pValue;
                case 1 -> BlockEntityResearchTable.this.maxProgress = pValue;
            }
        }

        @Override
        public int getCount() {
            return 8;
        }
    };

    @Override
    public ContainerData getFields() {
        return FIELDS;
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.thebeetrootmod.research_table");
    }
}
