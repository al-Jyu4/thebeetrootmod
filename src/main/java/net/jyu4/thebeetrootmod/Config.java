package net.jyu4.thebeetrootmod;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final String CATEGORY_GENERAL = "general";
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.BooleanValue enableSpawnTeleport;
    public static ForgeConfigSpec.BooleanValue enableDeathTeleport;

    static {
        BUILDER.push(CATEGORY_GENERAL);

        enableSpawnTeleport = BUILDER
                .comment("Enable teleportation to the spawn point.")
                .define("enableSpawnTeleport", true);

        enableDeathTeleport = BUILDER
                .comment("Enable teleportation to the death location.")
                .define("enableDeathTeleport", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
