package net.jyu4.thebeetrootmod.util;

import net.jyu4.thebeetrootmod.TheBeetrootMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TheBeetrootMod.MODID);
    ///------------------------------///
    public static final RegistryObject<SoundEvent> NEXUS_COMPLETION = registerSoundEvent("nexus_completion");
    public static final RegistryObject<SoundEvent> NEXUS_EMPOWERED = registerSoundEvent("nexus_empowered");
    ///------------------------------///
    public static final ForgeSoundType NEXUS_SOUNDS = new ForgeSoundType(0.1f, 1f,
            () -> SoundEvents.STONE_BREAK,
            () -> SoundEvents.STONE_STEP,
            ModSounds.NEXUS_COMPLETION,
            () -> SoundEvents.STONE_HIT,
            () -> SoundEvents.STONE_FALL
    );
    ///------------------------------///
    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        ResourceLocation id = new ResourceLocation(TheBeetrootMod.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
