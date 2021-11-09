/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla.mixins;

import com.kale_ko.better_vanilla.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;

@Mixin(CreeperIgniteGoal.class)
public class CreeperIgniteDefuse {
    @Inject(at = @At("TAIL"), method = "tick()V")
    public void tick(CallbackInfo info) {
        if (Main.config.creepers_defuse_in_water && ((CreeperIgniteGoalAccessor) ((CreeperIgniteDefuse) ((Object) this))).getCreeper().isTouchingWaterOrRain()) {
            ((CreeperIgniteGoalAccessor) ((CreeperIgniteDefuse) ((Object) this))).getCreeper().setFuseSpeed(-1);
        }
    }
}