/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BurringCampFireBlock extends CampfireBlock {
    public BurringCampFireBlock(Settings settings) {
        super(true, 0, settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (Main.config.campfires_set_fire && !entity.isFireImmune()) {
            entity.setOnFire(true);
            entity.setFireTicks(60);
        }

        super.onEntityCollision(state, world, pos, entity);
    }
}