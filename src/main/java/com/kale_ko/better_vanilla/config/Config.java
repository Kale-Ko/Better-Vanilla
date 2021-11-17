/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla.config;

import com.kale_ko.better_vanilla.Main;
import net.fabricmc.loader.api.FabricLoader;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.FloatListEntry;
import me.shedaniel.clothconfig2.gui.entries.StringListEntry;
import net.minecraft.text.TranslatableText;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public static final String version = "2";

    public static final File configFile = FabricLoader.getInstance().getConfigDir().resolve("better_vanilla.config").toFile();

    private Map<ConfigKey, Object> configRegistry = new HashMap<ConfigKey, Object>();

    public void create(ConfigKey config) {
        configRegistry.put(config, config.defaultValue);
    }

    public Object get(String id) {
        for (Map.Entry<ConfigKey, Object> kvp : configRegistry.entrySet()) {
            if (kvp.getKey().id.equalsIgnoreCase(id)) {
                return kvp.getValue();
            }
        }

        return null;
    }

    public void save() {
        Main.Console.info("Config is saving..");

        StringBuilder data = new StringBuilder("version=" + version + "\n");

        for (Map.Entry<ConfigKey, Object> kvp : configRegistry.entrySet()) {
            data.append(kvp.getKey().id + "=" + kvp.getValue().toString() + "\n");
        }

        try {
            FileWriter configWriter = new FileWriter(configFile);
            configWriter.write(data.toString());
            configWriter.close();
        } catch (IOException err) {
            Main.Console.error("Failed to save config");
        }

        Main.Console.info("Config has been saved!");
    }

    public void load() {
        Main.Console.info("Config is loading..");

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

                for (Map.Entry<ConfigKey, Object> kvp : configRegistry.entrySet()) {
                    if (keyValue[0].equalsIgnoreCase(kvp.getKey().id)) {
                        configRegistry.put(kvp.getKey(), keyValue[1]);
                    }
                }
            }
        } catch (Exception err) {
            Main.Console.error("Failed to parse config");
        }

        Main.Console.info("Config has been loaded!");
    }

    public ConfigBuilder buildMenu() {
        ConfigBuilder builder = ConfigBuilder.create().setTitle(new TranslatableText("better_vanilla.config.title"));

        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("better_vanilla.config.category.general"));
        ConfigCategory advanced = builder.getOrCreateCategory(new TranslatableText("better_vanilla.config.category.advanced"));

        for (Map.Entry<ConfigKey, Object> kvp : configRegistry.entrySet()) {
            if (kvp.getKey().type == ConfigType.String) {
                StringListEntry entry = builder.entryBuilder()
                        .startStrField(new TranslatableText("better_vanilla.config.option." + kvp.getKey().id + ".title"), (String) kvp.getValue())
                        .setDefaultValue((String) kvp.getKey().defaultValue)
                        .setTooltip(new TranslatableText("better_vanilla.config.option." + kvp.getKey().id + ".description"))
                        .setSaveConsumer(value -> {
                            configRegistry.remove(kvp.getKey());
                            configRegistry.put(kvp.getKey(), value);

                            save();
                        })
                        .build();

                if (kvp.getKey().category == com.kale_ko.better_vanilla.config.ConfigCategory.General) {
                    general.addEntry(entry);
                } else if (kvp.getKey().category == com.kale_ko.better_vanilla.config.ConfigCategory.Advanced) {
                    advanced.addEntry(entry);
                }
            } else if (kvp.getKey().type == ConfigType.Number) {
                FloatListEntry entry = builder.entryBuilder()
                        .startFloatField(new TranslatableText("better_vanilla.config.option." + kvp.getKey().id + ".title"), (Float) kvp.getValue())
                        .setDefaultValue((Float) kvp.getKey().defaultValue)
                        .setTooltip(new TranslatableText("better_vanilla.config.option." + kvp.getKey().id + ".description"))
                        .setSaveConsumer(value -> {
                            configRegistry.remove(kvp.getKey());
                            configRegistry.put(kvp.getKey(), value);

                            save();
                        })
                        .build();

                if (kvp.getKey().category == com.kale_ko.better_vanilla.config.ConfigCategory.General) {
                    general.addEntry(entry);
                } else if (kvp.getKey().category == com.kale_ko.better_vanilla.config.ConfigCategory.Advanced) {
                    advanced.addEntry(entry);
                }
            } else if (kvp.getKey().type == ConfigType.Boolean) {
                BooleanListEntry entry = builder.entryBuilder()
                        .startBooleanToggle(new TranslatableText("better_vanilla.config.option." + kvp.getKey().id + ".title"), (Boolean) kvp.getValue())
                        .setDefaultValue((Boolean) kvp.getKey().defaultValue)
                        .setTooltip(new TranslatableText("better_vanilla.config.option." + kvp.getKey().id + ".description"))
                        .setYesNoTextSupplier((Boolean value) -> {
                            return new TranslatableText("better_vanilla.config.value." + value.toString());
                        })
                        .setSaveConsumer(value -> {
                            configRegistry.remove(kvp.getKey());
                            configRegistry.put(kvp.getKey(), value);

                            save();
                        })
                        .build();

                if (kvp.getKey().category == com.kale_ko.better_vanilla.config.ConfigCategory.General) {
                    general.addEntry(entry);
                } else if (kvp.getKey().category == com.kale_ko.better_vanilla.config.ConfigCategory.Advanced) {
                    advanced.addEntry(entry);
                }
            }
        }

        builder.setFallbackCategory(general);

        return builder;
    }
}