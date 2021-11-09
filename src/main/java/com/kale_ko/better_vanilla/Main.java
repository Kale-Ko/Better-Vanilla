/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.kale_ko.better_vanilla.config.Config;

public class Main implements ModInitializer {
    public static final Logger Console = LogManager.getLogger("better-vanilla");
    public static final Config config = new Config();

    @Override
    public void onInitialize() {
        Console.info("Better vanilla is loading..");

        config.load();

        Console.info("Better vanilla has loaded!");
    }
}