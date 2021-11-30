package com.kale_ko.better_vanilla.config;

public class ConfigPair {
    public ConfigKey key;
    public Object value;

    public ConfigPair(ConfigKey key, Object value) {
        this.key = key;
        this.value = value;
    }
}