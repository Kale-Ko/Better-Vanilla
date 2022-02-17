package com.kale_ko.better_vanilla.mixins;

import com.kale_ko.better_vanilla.Main;
import com.kale_ko.better_vanilla.config.ConfigType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.mob.CreeperEntity;

@Mixin(CreeperEntity.class)
public class Creeper {
    @Inject(at = @At("HEAD"), method = "tick()V")
    public void tick(CallbackInfo info) {
        if (((CreeperEntity) ((Object) this)).isAlive()) {
            if ((Boolean) Main.config.get("creepers_ignite_from_fire", ConfigType.Boolean) && ((CreeperEntity) ((Object) this)).isOnFire()) {
                ((CreeperEntity) ((Object) this)).getDataTracker().set(CreeperEntityAccessor.getIGNITED(), true);
                ((CreeperEntity) ((Object) this)).setFuseSpeed(1);
            }

            if ((Boolean) Main.config.get("creepers_defuse_in_water", ConfigType.Boolean) && ((CreeperEntity) ((Object) this)).isTouchingWaterOrRain()) {
                ((CreeperEntity) ((Object) this)).getDataTracker().set(CreeperEntityAccessor.getIGNITED(), false);
                ((CreeperEntity) ((Object) this)).setFuseSpeed(-1);
            }
        }
    }
}