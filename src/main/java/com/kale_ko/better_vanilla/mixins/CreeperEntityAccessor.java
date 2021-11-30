/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.better_vanilla.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;

@Mixin(CreeperEntity.class)
public interface CreeperEntityAccessor {
    @Accessor("IGNITED")
    public static TrackedData<Boolean> getIGNITED() {
        throw new AssertionError();
    }
}