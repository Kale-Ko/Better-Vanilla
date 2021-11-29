package com.kale_ko.better_vanilla.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3f;

@Mixin(Camera.class)
public interface CameraAccessor {
    @Accessor
    Vec3f getHorizontalPlane();

    @Accessor
    Vec3f getDiagonalPlane();

    @Accessor
    Vec3f getVerticalPlane();
}