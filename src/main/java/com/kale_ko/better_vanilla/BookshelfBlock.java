/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BookshelfBlock extends Block {
    public static final IntProperty BOOKS;

    public BookshelfBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BOOKS, 3));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!Main.config.bookshelves_hold_books) {
            return ActionResult.FAIL;
        }

        if (state.get(BOOKS) > 0 && player.getInventory().getMainHandStack().isEmpty()) {
            if (player.getInventory().getOccupiedSlotWithRoomForStack(Items.BOOK.getDefaultStack()) == -1) {
                player.getInventory().setStack(player.getInventory().getEmptySlot(), Items.BOOK.getDefaultStack());
            } else {
                player.getInventory().getStack(player.getInventory().getOccupiedSlotWithRoomForStack(Items.BOOK.getDefaultStack())).setCount(player.getInventory().getStack(player.getInventory().getOccupiedSlotWithRoomForStack(Items.BOOK.getDefaultStack())).getCount() + 1);
            }

            state = state.with(BOOKS, state.get(BOOKS) - 1);
            world.setBlockState(pos, state, Block.NOTIFY_ALL);

            return ActionResult.SUCCESS;
        } else if (state.get(BOOKS) < 3 && player.getInventory().getMainHandStack().getItem() == Items.BOOK) {
            player.getInventory().getStack(player.getInventory().getOccupiedSlotWithRoomForStack(Items.BOOK.getDefaultStack())).setCount(player.getInventory().getStack(player.getInventory().getOccupiedSlotWithRoomForStack(Items.BOOK.getDefaultStack())).getCount() - 1);

            state = state.with(BOOKS, state.get(BOOKS) + 1);
            world.setBlockState(pos, state, Block.NOTIFY_ALL);

            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BOOKS);
    }

    static {
        BOOKS = IntProperty.of("books", 0, 3);
    }
}