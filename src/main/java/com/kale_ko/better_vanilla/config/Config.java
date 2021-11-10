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
import net.minecraft.text.TranslatableText;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    public static final String version = "1";

    public static final File configFile = FabricLoader.getInstance().getConfigDir().resolve("better_vanilla.config").toFile();

    public boolean remove_modded_notice = true;

    public boolean creepers_ignite_from_fire = true;
    public boolean creepers_defuse_in_water = true;

    public boolean infinity_plus_mending = true;

    public boolean bookshelves_hold_books = true;
    public boolean disable_custom_bookshelves = false;

    public void save() {
        String data = "version=" + version + "\n";

        data += "remove_modded_notice=" + remove_modded_notice + "\n";
        data += "creepers_ignite_from_fire=" + creepers_ignite_from_fire + "\n";
        data += "creepers_defuse_in_water=" + creepers_defuse_in_water + "\n";
        data += "infinity_plus_mending=" + infinity_plus_mending + "\n";
        data += "bookshelves_hold_books=" + bookshelves_hold_books + "\n";
        data += "disable_custom_bookshelves=" + disable_custom_bookshelves + "\n";

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

                if (keyValue[0].equals("remove_modded_notice")) {
                    remove_modded_notice = Boolean.parseBoolean(keyValue[1]);
                } else if (keyValue[0].equals("creepers_ignite_from_fire")) {
                    creepers_ignite_from_fire = Boolean.parseBoolean(keyValue[1]);
                } else if (keyValue[0].equals("creepers_defuse_in_water")) {
                    creepers_defuse_in_water = Boolean.parseBoolean(keyValue[1]);
                } else if (keyValue[0].equals("infinity_plus_mending")) {
                    infinity_plus_mending = Boolean.parseBoolean(keyValue[1]);
                } else if (keyValue[0].equals("bookshelves_hold_books")) {
                    bookshelves_hold_books = Boolean.parseBoolean(keyValue[1]);
                } else if (keyValue[0].equals("disable_custom_bookshelves")) {
                    disable_custom_bookshelves = Boolean.parseBoolean(keyValue[1]);
                }
            }
        } catch (Exception err) {
            Main.Console.error("Failed to parse config");
        }
    }

    public ConfigBuilder buildMenu() {
        ConfigBuilder builder = ConfigBuilder.create().setTitle(new TranslatableText("better_vanilla.config.title"));

        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("better_vanilla.config.category.general"));
        ConfigCategory advanced = builder.getOrCreateCategory(new TranslatableText("better_vanilla.config.category.advanced"));

        BooleanListEntry remove_modded_notice_entry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.option.remove_modded_notice.title"), remove_modded_notice)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.option.remove_modded_notice.description"))
                .setYesNoTextSupplier((Boolean value) -> {
                    return new TranslatableText("better_vanilla.config.value." + value.toString());
                })
                .build();

        BooleanListEntry creepers_ignite_from_fire_entry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.option.creepers_ignite_from_fire.title"), creepers_ignite_from_fire)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.option.creepers_ignite_from_fire.description"))
                .setYesNoTextSupplier((Boolean value) -> {
                    return new TranslatableText("better_vanilla.config.value." + value.toString());
                })
                .build();

        BooleanListEntry creepers_defuse_in_water_entry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.option.creepers_defuse_in_water.title"), creepers_defuse_in_water)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.option.creepers_defuse_in_water.description"))
                .setYesNoTextSupplier((Boolean value) -> {
                    return new TranslatableText("better_vanilla.config.value." + value.toString());
                })
                .build();

        BooleanListEntry infinity_plus_mending_entry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.option.infinity_plus_mending.title"), infinity_plus_mending)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.option.infinity_plus_mending.description"))
                .setYesNoTextSupplier((Boolean value) -> {
                    return new TranslatableText("better_vanilla.config.value." + value.toString());
                })
                .build();

        BooleanListEntry bookshelves_hold_books_entry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.option.bookshelves_hold_books.title"), bookshelves_hold_books)
                .setDefaultValue(true)
                .setTooltip(new TranslatableText("better_vanilla.config.option.bookshelves_hold_books.description"))
                .setYesNoTextSupplier((Boolean value) -> {
                    return new TranslatableText("better_vanilla.config.value." + value.toString());
                })
                .build();

        BooleanListEntry disable_custom_bookshelves_entry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("better_vanilla.config.option.disable_custom_bookshelves.title"), disable_custom_bookshelves)
                .setDefaultValue(false)
                .setTooltip(new TranslatableText("better_vanilla.config.option.disable_custom_bookshelves.description"))
                .requireRestart()
                .setYesNoTextSupplier((Boolean value) -> {
                    return new TranslatableText("better_vanilla.config.value." + value.toString());
                })
                .build();

        general.addEntry(remove_modded_notice_entry);
        general.addEntry(creepers_ignite_from_fire_entry);
        general.addEntry(creepers_defuse_in_water_entry);
        general.addEntry(infinity_plus_mending_entry);
        general.addEntry(bookshelves_hold_books_entry);
        advanced.addEntry(disable_custom_bookshelves_entry);

        builder.setFallbackCategory(general);
        builder.setSavingRunnable(() -> {
            remove_modded_notice = remove_modded_notice_entry.getValue();
            creepers_ignite_from_fire = creepers_ignite_from_fire_entry.getValue();
            creepers_defuse_in_water = creepers_defuse_in_water_entry.getValue();
            infinity_plus_mending = infinity_plus_mending_entry.getValue();
            bookshelves_hold_books = bookshelves_hold_books_entry.getValue();
            disable_custom_bookshelves = disable_custom_bookshelves_entry.getValue();

            save();
        });

        return builder;
    }
}