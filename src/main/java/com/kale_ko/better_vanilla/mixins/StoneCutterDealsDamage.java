/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla.mixins;

import com.kale_ko.better_vanilla.CustomDamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.block.BlockState;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(StonecutterBlock.class)
public class StoneCutterDealsDamage {
    @Inject(at = @At("HEAD"), method = "onEntityCollision()Z")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.damage(new CustomDamageSource("cutByStoneCutter"), 0.2f);
    }
}