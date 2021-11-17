/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla.mixins;

import com.kale_ko.better_vanilla.Main;
import com.kale_ko.better_vanilla.config.ConfigType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class RemoveModdedNotice {
    @Inject(at = @At("HEAD"), method = "isModded()Z", cancellable = true)
    public void isModded(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(!(Boolean) Main.config.get("remove_modded_notice", ConfigType.Boolean));
    }
}