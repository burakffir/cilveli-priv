package me.batuhan.cilveligorunmez.mixin;

import me.batuhan.cilveligorunmez.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    private void overrideInvisibility(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (!ModConfig.INSTANCE.isEnabled) return;
        
        Entity target = (Entity) (Object) this;

        // biz hariç diger oyunculara yari saydamlik (spectator gibi) uygula
        if (target instanceof PlayerEntity && target != MinecraftClient.getInstance().player) {
            if (ModConfig.INSTANCE.visualMode == ModConfig.VisualMode.TRANSLUCENT || 
                ModConfig.INSTANCE.visualMode == ModConfig.VisualMode.BOTH) {
                cir.setReturnValue(false); 
            }
        }
    }

    @Inject(method = "isGlowing", at = @At("HEAD"), cancellable = true)
    private void setBypassGlow(CallbackInfoReturnable<Boolean> cir) {
        if (!ModConfig.INSTANCE.isEnabled) return;
        
        Entity target = (Entity) (Object) this;
        
        // gercekten gorunmezse adami isiklandir/parlat
        if (target instanceof PlayerEntity && target != MinecraftClient.getInstance().player && target.isInvisible()) {
            if (ModConfig.INSTANCE.visualMode == ModConfig.VisualMode.GLOWING || 
                ModConfig.INSTANCE.visualMode == ModConfig.VisualMode.BOTH) {
                cir.setReturnValue(true);
            }
        }
    }
}