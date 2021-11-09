U/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla;

import net.minecraft.block.Block;

public class BookshelfBlock extends Block {
    public BookshelfBlock(Settings settings) {
        super(settings);

        Main.Console.info("Created bookshelf!");
    }
}