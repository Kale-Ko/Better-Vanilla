package com.kale_ko.better_vanilla.config;

import com.kale_ko.better_vanilla.Main;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ConfigMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            return Main.config.buildMenu().setParentScreen(parent).build();
        };
    }
}