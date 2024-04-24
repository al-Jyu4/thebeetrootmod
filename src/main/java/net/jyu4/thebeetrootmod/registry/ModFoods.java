package net.jyu4.thebeetrootmod.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties GOLDEN_BEETROOT = (new FoodProperties.Builder()).nutrition(4).saturationMod(1.2F).effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEat().build();

    public static final FoodProperties CANDY_BEETROOT = beetroot().build();
    public static final FoodProperties PURPLE_BEETROOT = beetroot().build();
    public static final FoodProperties YELLOW_BEETROOT = beetroot().build();

    private static FoodProperties.Builder beetroot() {
        return (new FoodProperties.Builder()).nutrition(1).saturationMod(0.6F);
    }
}
