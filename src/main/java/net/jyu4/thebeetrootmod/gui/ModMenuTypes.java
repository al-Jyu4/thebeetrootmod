package net.jyu4.thebeetrootmod.gui;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, TheBeetrootMod.MODID);

    public static final RegistryObject<MenuType<WorkstationMenu>> WORKSTATION = registerMenuType("thebeetrootmod.workstation", WorkstationMenu::new);
    public static final RegistryObject<MenuType<ShrineMenu>> MENU_SHRINE = registerMenuType("thebeetrootmod.shrine", ShrineMenu::new);
    public static final RegistryObject<MenuType<AltarMenu>> ALTAR_MENU = registerMenuType("thebeetrootmod.altar", AltarMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
