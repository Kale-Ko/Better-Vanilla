package com.kale_ko.better_vanilla.config;

import com.kale_ko.better_vanilla.Main;
import net.fabricmc.loader.api.FabricLoader;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import net.minecraft.text.TranslatableText;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    public static final File configFile = FabricLoader.getInstance().getConfigDir().resolve("better_vanilla.config").toFile();

    public boolean no_modded_info = true;

    public boolean creepers_ignite_from_fire = true;
    public boolean creepers_defuse_in_water = true;

    public void save() {
        String data = "";

        data += "no_modded_info=" + no_modded_info + "\n";
        data += "creepers_ignite_from_fire=" + creepers_ignite_from_fire + "\n";
        data += "creepers_defuse_in_water=" + creepers_defuse_in_water + "\n";

        try {
            FileWriter configWriter = new FileWriter(configFile);
            configWriter.write(data);
            configWriter.close();
        } catch (IOException err) {
            Main.Console.error("Failed to save config");
        }

        Main.Console.info("Config has been saved!");
    }

    public void load() {
        if (!configFile.exists()) {
            save();

            return;
        }

        String data = "";

        try {
            FileReader configReader = new FileReader(configFile);

            char[] rawdata = new char[(int) configFile.length()];
            configReader.read(rawdata);
            configReader.close();

            data = new String(rawdata);
        } catch (IOException err) {
            Main.Console.error("Failed to load config");
        }

        try {
            String[] lines = data.split("\n");

            for (String line : lines) {
                String[] keyValue = line.split("=");

                if (keyValue[0].equals("no_modded_info")) {
                    no_modded_info = Boolean.parseBoolean(keyValue[1]);
                } else if (keyValue[0].equals("creepers_ignite_from_fire")) {
                    creepers_ignite_from_fire = Boolean.parseBoolean(keyValue[1]);
                } else if (keyValue[0].equals("creepers_defuse_in_water")) {
                    creepers_defuse_in_water = Boolean.parseBoolean(keyValue[1]);
                }
            }
        } catch (Exception err) {
            Main.Console.error("Failed to parse config");
        }
    }

    public ConfigBuilder buildMenu() {
        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle(new TranslatableText("better_vanilla.config.title"));
                // .setDefaultBackgroundTexture(arg0);

        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("better_vanilla.config.general"));

        BooleanListEntry no_modded_info_entry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.no_modded_info.title"), no_modded_info)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.no_modded_info.description"))
                .build();

        BooleanListEntry creepers_ignite_from_fire_entry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.creepers_ignite_from_fire.title"), creepers_ignite_from_fire)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.creepers_ignite_from_fire.description"))
                .build();

        BooleanListEntry creepers_defuse_in_water_entry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.creepers_defuse_in_water.title"), creepers_defuse_in_water)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.creepers_defuse_in_water.description"))
                .build();

        general.addEntry(no_modded_info_entry);
        general.addEntry(creepers_ignite_from_fire_entry);
        general.addEntry(creepers_defuse_in_water_entry);

        builder.setSavingRunnable(() -> {
            no_modded_info = no_modded_info_entry.getValue();
            creepers_ignite_from_fire = creepers_ignite_from_fire_entry.getValue();
            creepers_defuse_in_water = creepers_defuse_in_water_entry.getValue();

            save();
        });

        return builder;
    }
}