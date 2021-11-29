package com.kale_ko.better_vanilla.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Accessor
    Boolean getRenderingPanorama();

    @Accessor
    MinecraftClient getClient();

    @Accessor
    float getMovementFovMultiplier();

    @Accessor
    float getLastMovementFovMultiplier();
}