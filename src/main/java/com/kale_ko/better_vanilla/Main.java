package com.kale_ko.better_vanilla;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import java.util.function.ToIntFunction;
import com.kale_ko.better_vanilla.config.Config;
import com.kale_ko.better_vanilla.config.ConfigCategory;
import com.kale_ko.better_vanilla.config.ConfigKey;
import com.kale_ko.better_vanilla.config.ConfigType;

public class Main implements ModInitializer {
    public static final String MOD_ID = "better_vanilla";
    public static final Logger Console = LogManager.getLogger(MOD_ID);
    public static final Config config = new Config();

    public static Boolean zoomed = false;
    public static KeyBinding zoomKeybind;

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
        config.create(new ConfigKey("zoom_enabled", ConfigCategory.General, ConfigType.Boolean, "true"));
        config.create(new ConfigKey("zoom_amount", ConfigCategory.General, ConfigType.Double, "4.00"));

        config.load();

        Block bookshelf = new BookshelfBlock(AbstractBlock.Settings.of(Material.WOOD).strength(1.5F).sounds(BlockSoundGroup.WOOD));
        Registry.register(Registry.BLOCK, Registry.BLOCK.getRawId(Blocks.BOOKSHELF), MOD_ID + ":bookshelf", bookshelf);
        BlockItem bookshelf_item = new BlockItem(bookshelf, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
        Registry.register(Registry.ITEM, Registry.ITEM.getRawId(Items.BOOKSHELF), MOD_ID + ":bookshelf", bookshelf_item);

        Block stonecutter = new DamagingStonecutterBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.5F));
        Registry.register(Registry.BLOCK, Registry.BLOCK.getRawId(Blocks.STONECUTTER), MOD_ID + ":stonecutter", stonecutter);
        BlockItem stonecutter_item = new BlockItem(stonecutter, new Item.Settings().group(ItemGroup.DECORATIONS));
        Registry.register(Registry.ITEM, Registry.ITEM.getRawId(Items.STONECUTTER), MOD_ID + ":stonecutter", stonecutter_item);

        Block campfire = new BurringCampFireBlock(true, AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(15)).nonOpaque());
        Registry.register(Registry.BLOCK, Registry.BLOCK.getRawId(Blocks.CAMPFIRE), MOD_ID + ":campfire", campfire);
        BlockItem campfire_item = new BlockItem(campfire, new Item.Settings().group(ItemGroup.DECORATIONS));
        Registry.register(Registry.ITEM, Registry.ITEM.getRawId(Items.CAMPFIRE), MOD_ID + ":campfire", campfire_item);

        Block soul_campfire = new BurringCampFireBlock(false, AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(10)).nonOpaque());
        Registry.register(Registry.BLOCK, Registry.BLOCK.getRawId(Blocks.SOUL_CAMPFIRE), MOD_ID + ":soul_campfire", soul_campfire);
        BlockItem soul_campfire_item = new BlockItem(soul_campfire, new Item.Settings().group(ItemGroup.DECORATIONS));
        Registry.register(Registry.ITEM, Registry.ITEM.getRawId(Items.SOUL_CAMPFIRE), MOD_ID + ":soul_campfire", soul_campfire_item);

        zoomKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(MOD_ID + ".keybinds.key.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, MOD_ID + ".keybinds.catogory"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            zoomed = zoomKeybind.isPressed();
        });

        Console.info("Better vanilla has loaded!");
    }
}