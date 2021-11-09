package com.kale_ko.better_vanilla.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;

@Mixin(CreeperIgniteGoal.class)
public interface CreeperIgniteGoalAccessor {
    @Accessor
    CreeperEntity getCreeper();
}