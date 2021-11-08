package com.kale_ko.better_vanilla;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
    public static final Logger Console = LogManager.getLogger("better-vanilla");

    @Override
    public void onInitialize() {
        Console.info("Better vanilla is loading..");
    }
}