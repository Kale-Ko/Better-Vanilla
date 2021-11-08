package com.kale_ko.better_vanilla.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;

@Mixin(CreeperEntity.class)
public class Creeper {
    @Accessor("IGNITED")
    public static TrackedData<Boolean> getIGNITED() {
        throw new AssertionError();
    }

    @Inject(at = @At("HEAD"), method = "tick()V")
    public void tick(CallbackInfo info) {
        if (((CreeperEntity) ((Object) this)).isAlive()) {
            if (((CreeperEntity) ((Object) this)).isOnFire()) {
                ((CreeperEntity) ((Object) this)).ignite();
            }

            if (((CreeperEntity) ((Object) this)).isTouchingWaterOrRain()) {
                ((CreeperEntity) ((Object) this)).getDataTracker().set(Creeper.getIGNITED(), false);
            }
        }
    }
}