package com.kale_ko.better_vanilla.mixins;

import com.kale_ko.better_vanilla.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class RemoveModdedWindowNotice {
    @Redirect(method = "getWindowTitle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;isModded()Z"))
    public boolean isModded(MinecraftClient client) {
        return !Main.config.no_modded_info;
    }
}