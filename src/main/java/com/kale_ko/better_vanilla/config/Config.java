package com.kale_ko.better_vanilla.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.text.TranslatableText;

public class Config {
    public boolean no_modded_info = true;

    public boolean creepers_ignite_from_fire = true;
    public boolean creepers_defuse_in_water = true;

    public ConfigBuilder buildMenu() {
        ConfigBuilder builder = ConfigBuilder.create().setTitle(new TranslatableText("better_vanilla.config.title"));

        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("better_vanilla.config.general"));

        general.addEntry(builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.no_modded_info.title"), no_modded_info)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.no_modded_info.description"))
                .build());

        general.addEntry(builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.creepers_ignite_from_fire.title"), creepers_ignite_from_fire)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.creepers_ignite_from_fire.description"))
                .build());

        general.addEntry(builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.creepers_defuse_in_water.title"), creepers_defuse_in_water)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.creepers_defuse_in_water.description"))
                .build());

        return builder;
    }
}