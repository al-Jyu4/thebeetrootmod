package net.jyu4.thebeetrootmod;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> ECHO_BEETROOT_TELEPORT_SPAWN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ECHO_BEETROOT_TELEPORT_DEATH;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CHORUS_BEETROOT_TELEPORT;

    static {
        BUILDER.push("the beetroot mod configs");

        ECHO_BEETROOT_TELEPORT_SPAWN = BUILDER.comment("Enable echo beetroots teleport to spawn").translation("toggle_echo_beetroots_spawn").define("toggle_echo_beetroots_spawn", true);
        ECHO_BEETROOT_TELEPORT_DEATH = BUILDER.comment("Enable echo beetroots teleport to death location").translation("toggle_echo_beetroots_death").define("toggle_echo_beetroots_death", true);
        CHORUS_BEETROOT_TELEPORT = BUILDER.comment("Enable chorus beetroots teleport").translation("toggle_chorus_beetroot_teleport").define("toggle_chorus_beetroot_teleport", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
