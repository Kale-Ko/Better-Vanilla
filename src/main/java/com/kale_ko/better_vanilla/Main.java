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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.registry.DefaultedRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.function.ToIntFunction;
import com.kale_ko.better_vanilla.config.Config;
import com.kale_ko.better_vanilla.config.ConfigCategory;
import com.kale_ko.better_vanilla.config.ConfigKey;
import com.kale_ko.better_vanilla.config.ConfigType;

public class Main implements ModInitializer {
    public static final Logger Console = LogManager.getLogger("better_vanilla");
    public static final Config config = new Config();

    public static Boolean zoomed = false;

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> {
            return (Boolean) state.get(Properties.LIT) ? litLevel : 0;
        };
    }

    @Override
    public void onInitialize() {
        Console.info("Better vanilla is loading..");

        config.create(new ConfigKey("remove_modded_notice", ConfigCategory.General, ConfigType.Boolean, "true"));
        config.create(new ConfigKey("creepers_ignite_from_fire", ConfigCategory.General, ConfigType.Boolean, "true"));
        config.create(new ConfigKey("creepers_defuse_in_water", ConfigCategory.General, ConfigType.Boolean, "true"));
        config.create(new ConfigKey("infinity_plus_mending", ConfigCategory.General, ConfigType.Boolean, "true"));
        config.create(new ConfigKey("bookshelves_hold_books", ConfigCategory.General, ConfigType.Boolean, "true"));
        config.create(new ConfigKey("stonecutters_deal_damage", ConfigCategory.General, ConfigType.Boolean, "true"));
        config.create(new ConfigKey("campfires_set_fire", ConfigCategory.General, ConfigType.Boolean, "true"));
        config.create(new ConfigKey("disable_custom_blocks", ConfigCategory.Advanced, ConfigType.Boolean, "false"));

        config.load();

        if (!(Boolean) config.get("disable_custom_blocks", ConfigType.Boolean)) {
            Block bookshelf = new BookshelfBlock(AbstractBlock.Settings.of(Material.WOOD).strength(1.5F).sounds(BlockSoundGroup.WOOD));
            DefaultedRegistry.register(DefaultedRegistry.BLOCK, DefaultedRegistry.BLOCK.getRawId(Blocks.BOOKSHELF), "bookshelf", bookshelf);
            BlockItem bookshelf_item = new BlockItem(bookshelf, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
            DefaultedRegistry.register(DefaultedRegistry.ITEM, DefaultedRegistry.ITEM.getRawId(Items.BOOKSHELF), "bookshelf", bookshelf_item);

            Block stonecutter = new DamagingStonecutterBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.5F));
            DefaultedRegistry.register(DefaultedRegistry.BLOCK, DefaultedRegistry.BLOCK.getRawId(Blocks.STONECUTTER), "stonecutter", stonecutter);
            BlockItem stonecutter_item = new BlockItem(stonecutter, new Item.Settings().group(ItemGroup.DECORATIONS));
            DefaultedRegistry.register(DefaultedRegistry.ITEM, DefaultedRegistry.ITEM.getRawId(Items.STONECUTTER), "stonecutter", stonecutter_item);

            Block campfire = new BurringCampFireBlock(true, AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(15)).nonOpaque());
            DefaultedRegistry.register(DefaultedRegistry.BLOCK, DefaultedRegistry.BLOCK.getRawId(Blocks.CAMPFIRE), "campfire", campfire);
            BlockItem campfire_item = new BlockItem(campfire, new Item.Settings().group(ItemGroup.DECORATIONS));
            DefaultedRegistry.register(DefaultedRegistry.ITEM, DefaultedRegistry.ITEM.getRawId(Items.CAMPFIRE), "campfire", campfire_item);

            Block soul_campfire = new BurringCampFireBlock(false, AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(10)).nonOpaque());
            DefaultedRegistry.register(DefaultedRegistry.BLOCK, DefaultedRegistry.BLOCK.getRawId(Blocks.SOUL_CAMPFIRE), "soul_campfire", soul_campfire);
            BlockItem soul_campfire_item = new BlockItem(soul_campfire, new Item.Settings().group(ItemGroup.DECORATIONS));
            DefaultedRegistry.register(DefaultedRegistry.ITEM, DefaultedRegistry.ITEM.getRawId(Items.SOUL_CAMPFIRE), "soul_campfire", soul_campfire_item);
        }

        Console.info("Better vanilla has loaded!");
    }
}