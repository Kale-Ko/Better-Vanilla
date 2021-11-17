/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla.config;

public class ConfigKey {
    public String id;

    public ConfigCategory category;
    public ConfigType type;

    public Object defaultValue;

    public ConfigKey(String id, ConfigCategory category, ConfigType type, Object defaultValue) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.defaultValue = defaultValue;
    }
}