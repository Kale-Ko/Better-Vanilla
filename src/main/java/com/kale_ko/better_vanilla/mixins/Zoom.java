package com.kale_ko.better_vanilla.mixins;

import com.kale_ko.better_vanilla.Main;
import com.kale_ko.better_vanilla.config.ConfigType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.render.Camera;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

@Mixin(Camera.class)
public class Zoom {
    @Inject(at = @At("HEAD"), method = "getProjection()Lnet/minecraft/client/render/Camera$Projection;", cancellable = true)
    public void getProjection(CallbackInfoReturnable<Camera.Projection> info) throws Exception {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        double d = (double) minecraftClient.getWindow().getFramebufferWidth() / (double) minecraftClient.getWindow().getFramebufferHeight();
        double e = Math.tan((minecraftClient.options.fov / getZoom()) * 0.01745329238474369 / 2.0) * (double) 0.05f;
        double f = e * d;
        Vec3d vec3d = new Vec3d(((CameraAccessor) ((Camera) ((Object) this))).getHorizontalPlane()).multiply(0.05f);
        Vec3d vec3d2 = new Vec3d(((CameraAccessor) ((Camera) ((Object) this))).getDiagonalPlane()).multiply(f);
        Vec3d vec3d3 = new Vec3d(((CameraAccessor) ((Camera) ((Object) this))).getVerticalPlane()).multiply(e);

        info.setReturnValue(
                (Camera.Projection) Camera.Projection.class.getConstructors()[0].newInstance(vec3d, vec3d2, vec3d3));
    }

    public double getZoom() {
        if ((Boolean) Main.config.get("zoom_enabled", ConfigType.Boolean) && Main.zoomed) {
            return (double) Main.config.get("zoom_amount", ConfigType.Number);
        } else {
            return 1d;
        }
    }
}