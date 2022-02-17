package com.kale_ko.better_vanilla;

import com.kale_ko.better_vanilla.config.ConfigType;
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
        if ((Boolean) Main.config.get("stonecutters_deal_damage", ConfigType.Boolean)) {
            entity.damage(new CustomDamageSource("cutByStoneCutter"), 1.25f);
        }
    }
}