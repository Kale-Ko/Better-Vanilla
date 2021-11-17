/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla;

import net.minecraft.block.BlockState;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DamagingStonecutterBlock extends StonecutterBlock {
    public DamagingStonecutterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (Main.config.stonecutters_deal_damage) {
            entity.damage(new CustomDamageSource("cutByStoneCutter"), 0.2f);
        }
    }
}