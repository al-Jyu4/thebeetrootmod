package net.jyu4.thebeetrootmod.gui;

import net.jyu4.thebeetrootmod.block.blockentity.BlockEntityBase;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkHooks;

public class BlockEntityContainerProvider implements MenuProvider {


    private ContainerCreator container;
    private BlockEntityBase blockEntity;

    public BlockEntityContainerProvider(ContainerCreator container, BlockEntityBase blockEntity) {
        this.container = container;
        this.blockEntity = blockEntity;
    }

    @Override
    public Component getDisplayName() {
        return blockEntity.getDisplayName();
    }

    public static void openGui(ServerPlayer player, BlockEntityBase blockEntity, ContainerCreator containerCreator) {
        NetworkHooks.openScreen(player, new BlockEntityContainerProvider(containerCreator, blockEntity), packetBuffer -> packetBuffer.writeBlockPos(blockEntity.getBlockPos()));
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
        return container.create(i, playerInventory, playerEntity);
    }

    public interface ContainerCreator {
        AbstractContainerMenu create(int i, Inventory playerInventory, Player playerEntity);
    }
}
