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
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.enchantment.MendingEnchantment;

@Mixin(InfinityEnchantment.class)
public class InfinityPlusMending {
    @Inject(at = @At("HEAD"), method = "canAccept(Lnet/minecraft/enchantment/Enchantment;)Z", cancellable = true)
    public void canAccept(Enchantment other, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(other instanceof MendingEnchantment ? (Boolean) Main.config.get("infinity_plus_mending", ConfigType.Boolean) : !(other instanceof InfinityEnchantment));
    }
}
