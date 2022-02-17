package com.kale_ko.better_vanilla.config;

import com.kale_ko.better_vanilla.Main;
import net.fabricmc.loader.api.FabricLoader;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import me.shedaniel.clothconfig2.gui.entries.FloatListEntry;
import me.shedaniel.clothconfig2.gui.entries.StringListEntry;
import net.minecraft.text.TranslatableText;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Config {
    public static final String version = "2";

    public static final File configFile = FabricLoader.getInstance().getConfigDir().resolve("better_vanilla.config").toFile();

    private List<ConfigPair> configRegistry = new ArrayList<ConfigPair>();
    public static int configKeys = 0;

    public void create(ConfigKey config) {
        configRegistry.add(new ConfigPair(config, config.defaultValue.toString()));
    }

    public Object get(String id, ConfigType type) {
        for (ConfigPair kvp : configRegistry) {
            if (kvp.key.id.equalsIgnoreCase(id)) {
                if (type == ConfigType.String) return kvp.value;
                else if (type == ConfigType.Float) return Float.parseFloat((String)kvp.value);
                else if (type == ConfigType.Double) return Double.parseDouble((String)kvp.value);
                else if (type == ConfigType.Boolean) return Boolean.parseBoolean((String)kvp.value);
            }
        }

        return null;
    }

    public void save() {
        Main.Console.info("Config is saving..");

        StringBuilder data = new StringBuilder("version=" + version + "\n");

        for (ConfigPair kvp : configRegistry) {
            data.append(kvp.key.id + "=" + kvp.value.toString() + "\n");
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

                for (ConfigPair kvp : configRegistry) {
                    if (keyValue[0].equalsIgnoreCase(kvp.key.id)) {
                        configRegistry.set(configRegistry.indexOf(kvp), new ConfigPair(kvp.key, keyValue[1]));
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

        for (ConfigPair kvp : configRegistry) {
            if (kvp.key.type == ConfigType.String) {
                StringListEntry entry = builder.entryBuilder()
                        .startStrField(new TranslatableText("better_vanilla.config.option." + kvp.key.id + ".title"), (String) kvp.value)
                        .setDefaultValue((String) kvp.key.defaultValue)
                        .setTooltip(new TranslatableText("better_vanilla.config.option." + kvp.key.id + ".description"))
                        .setSaveConsumer(value -> {
                            configRegistry.set(configRegistry.indexOf(kvp), new ConfigPair(kvp.key, value));
                        })
                        .build();

                if (kvp.key.category == com.kale_ko.better_vanilla.config.ConfigCategory.General) {
                    general.addEntry(entry);
                } else if (kvp.key.category == com.kale_ko.better_vanilla.config.ConfigCategory.Advanced) {
                    entry.setRequiresRestart(true);

                    advanced.addEntry(entry);
                }
            } else if (kvp.key.type == ConfigType.Float) {
                FloatListEntry entry = builder.entryBuilder()
                        .startFloatField(new TranslatableText("better_vanilla.config.option." + kvp.key.id + ".title"), Float.parseFloat((String) kvp.value))
                        .setDefaultValue(Float.parseFloat((String) kvp.key.defaultValue))
                        .setTooltip(new TranslatableText("better_vanilla.config.option." + kvp.key.id + ".description"))
                        .setSaveConsumer(value -> {
                            configRegistry.set(configRegistry.indexOf(kvp), new ConfigPair(kvp.key, value.toString()));
                        })
                        .build();

                if (kvp.key.category == com.kale_ko.better_vanilla.config.ConfigCategory.General) {
                    general.addEntry(entry);
                } else if (kvp.key.category == com.kale_ko.better_vanilla.config.ConfigCategory.Advanced) {
                    entry.setRequiresRestart(true);

                    advanced.addEntry(entry);
                }
            } else if (kvp.key.type == ConfigType.Double) {
                DoubleListEntry entry = builder.entryBuilder()
                        .startDoubleField(new TranslatableText("better_vanilla.config.option." + kvp.key.id + ".title"), Double.parseDouble((String) kvp.value))
                        .setDefaultValue(Double.parseDouble((String) kvp.key.defaultValue))
                        .setTooltip(new TranslatableText("better_vanilla.config.option." + kvp.key.id + ".description"))
                        .setSaveConsumer(value -> {
                            configRegistry.set(configRegistry.indexOf(kvp), new ConfigPair(kvp.key, value.toString()));
                        })
                        .build();

                if (kvp.key.category == com.kale_ko.better_vanilla.config.ConfigCategory.General) {
                    general.addEntry(entry);
                } else if (kvp.key.category == com.kale_ko.better_vanilla.config.ConfigCategory.Advanced) {
                    entry.setRequiresRestart(true);

                    advanced.addEntry(entry);
                }
            } else if (kvp.key.type == ConfigType.Boolean) {
                BooleanListEntry entry = builder.entryBuilder()
                        .startBooleanToggle(new TranslatableText("better_vanilla.config.option." + kvp.key.id + ".title"), Boolean.parseBoolean((String) kvp.value))
                        .setDefaultValue(Boolean.parseBoolean((String) kvp.key.defaultValue))
                        .setTooltip(new TranslatableText("better_vanilla.config.option." + kvp.key.id + ".description"))
                        .setYesNoTextSupplier((Boolean value) -> {
                            return new TranslatableText("better_vanilla.config.value." + value.toString());
                        })
                        .setSaveConsumer(value -> {
                            configRegistry.set(configRegistry.indexOf(kvp), new ConfigPair(kvp.key, value.toString()));
                        })
                        .build();

                if (kvp.key.category == com.kale_ko.better_vanilla.config.ConfigCategory.General) {
                    general.addEntry(entry);
                } else if (kvp.key.category == com.kale_ko.better_vanilla.config.ConfigCategory.Advanced) {
                    entry.setRequiresRestart(true);

                    advanced.addEntry(entry);
                }
            }
        }

        BooleanListEntry reset_all_entry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.option.reset_all.title"), false)
                .setDefaultValue(false)
                .setTooltip(new TranslatableText("better_vanilla.config.option.reset_all.description"))
                .setYesNoTextSupplier((Boolean value) -> {
                    return new TranslatableText("better_vanilla.config.value." + value.toString());
                })
                .setSaveConsumer(value -> {
                    if (value) {
                        for (ConfigPair kvp : configRegistry) {
                            configRegistry.set(configRegistry.indexOf(kvp), new ConfigPair(kvp.key, kvp.key.defaultValue));
                        }
                    }
                })
                .build();

        advanced.addEntry(reset_all_entry);

        builder.setSavingRunnable(this::save);
        builder.setFallbackCategory(general);

        return builder;
    }
}