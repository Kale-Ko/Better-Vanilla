package com.kale_ko.better_vanilla.mixins;

import com.kale_ko.better_vanilla.Main;
import com.kale_ko.better_vanilla.config.ConfigType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.util.math.MathHelper;

@Mixin(GameRenderer.class)
public class RendererZoom {
    @Inject(at = @At("HEAD"), method = "getFov(Lnet/minecraft/client/render/CameraFZ)D", cancellable = true)
    private void getFov(Camera camera, float f, boolean bl, CallbackInfoReturnable<Double> info) {
        MinecraftClient minecraftClient = ((GameRendererAccessor) ((GameRenderer) ((Object) this))).getClient();
        CameraSubmersionType g;

        if (((GameRendererAccessor) ((GameRenderer) ((Object) this))).getRenderingPanorama()) {
            info.setReturnValue(90.0);
        }

        double d = 70.0;

        if (bl) {
            d = minecraftClient.options.fov;
            d *= (double)MathHelper.lerp(f, ((GameRendererAccessor) ((GameRenderer) ((Object) this))).getLastMovementFovMultiplier(), ((GameRendererAccessor) ((GameRenderer) ((Object) this))).getMovementFovMultiplier());
        }

        if (camera.getFocusedEntity() instanceof LivingEntity livingEntity && ((LivingEntity)camera.getFocusedEntity()).isDead()) {
            float g2 = Math.min((float)((LivingEntity)camera.getFocusedEntity()).deathTime + f, 20.0f);
            d /= (double)((1.0f - 500.0f / (g2 + 500.0f)) * 2.0f + 1.0f);
        }

        if ((g = camera.getSubmersionType()) == CameraSubmersionType.LAVA || g == CameraSubmersionType.WATER) {
            d *= (double)MathHelper.lerp(minecraftClient.options.fovEffectScale, 1.0f, 0.85714287f);
        }

        info.setReturnValue(d / getZoom());
    }

    public double getZoom() {
        if ((Boolean) Main.config.get("zoom_enabled", ConfigType.Boolean) && Main.zoomed) {
            return (double) Main.config.get("zoom_amount", ConfigType.Double);
        } else {
            return 1d;
        }
    }
}