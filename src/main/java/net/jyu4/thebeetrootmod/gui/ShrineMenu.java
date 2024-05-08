package net.jyu4.thebeetrootmod.gui;

import net.jyu4.thebeetrootmod.block.ModBlocks;
import net.jyu4.thebeetrootmod.recipe.ModRecipeType;
import net.jyu4.thebeetrootmod.recipe.WorkstationRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class ShrineMenu extends ItemCombinerMenu {

    private final Level level;
    @Nullable
    private WorkstationRecipe selectedRecipe;
    private final List<WorkstationRecipe> recipes;

    public ShrineMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf a) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    public ShrineMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(ModMenuTypes.MENU_SHRINE.get(), pContainerId, pPlayerInventory, pAccess);
        this.level = pPlayerInventory.player.level();
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(ModRecipeType.WORKSTATION.get());
    }

    protected boolean isValidBlock(BlockState pState) {
        return pState.is(ModBlocks.SHRINE.get());
    }

    protected boolean mayPickup(Player pPlayer, boolean pHasStack) {
        return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
    }
    protected void onTake(Player pPlayer, ItemStack pStack) {
        pStack.onCraftedBy(pPlayer.level(), pPlayer, pStack.getCount());
        this.resultSlots.awardUsedRecipes(pPlayer, this.getRelevantItems());

        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.shrinkStackInSlot(2);
        this.shrinkStackInSlot(3);

        this.access.execute((p_40263_, p_40264_) -> p_40263_.levelEvent(1044, p_40264_, 0));
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(this.inputSlots.getItem(0),
                this.inputSlots.getItem(1),
                this.inputSlots.getItem(2),
                this.inputSlots.getItem(3));
    }

    private void shrinkStackInSlot(int pIndex) {
        ItemStack $$1 = this.inputSlots.getItem(pIndex);
        if (!$$1.isEmpty()) {
            $$1.shrink(1);
            this.inputSlots.setItem(pIndex, $$1);
        }

    }

    public void createResult() {

        List<WorkstationRecipe> $$0 = this.level.getRecipeManager().getRecipesFor(ModRecipeType.WORKSTATION.get(), this.inputSlots, this.level);
        if ($$0.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            WorkstationRecipe $$1 = $$0.get(0);
            ItemStack $$2 = $$1.assemble(this.inputSlots, this.level.registryAccess());
            if ($$2.isItemEnabled(this.level.enabledFeatures())) {
                this.selectedRecipe = $$1;
                this.resultSlots.setRecipeUsed($$1);
                this.resultSlots.setItem(0, $$2);
            }
        }

    }

    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 26, 48, itemStack -> true)
                .withSlot(1, 44, 48, itemStack -> true)
                .withSlot(2, 62, 48, itemStack -> true)
                .withSlot(3, 80, 48, itemStack -> true)
                .withResultSlot(4, 134, 48)
                .build();
    }
}
