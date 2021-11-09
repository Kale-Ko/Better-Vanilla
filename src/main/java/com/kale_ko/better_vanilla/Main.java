/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
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

        if (true) {
            Block bookshelf = new BookshelfBlock(AbstractBlock.Settings.of(Material.WOOD).strength(1.5F).sounds(BlockSoundGroup.WOOD));
            Registry.register(Registry.BLOCK, 144, "bookshelf", bookshelf);

            BlockItem bookshelf_item = new BlockItem(bookshelf, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
            Registry.register(Registry.ITEM, 233, "bookshelf", bookshelf_item);
        }

        Console.info("Better vanilla has loaded!");
    }
}