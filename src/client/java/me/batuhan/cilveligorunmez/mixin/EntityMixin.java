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
        
        Entity targetEntity = (Entity) (Object) this;

        // Eger kendi karakterimiz degilse ve baska bir oyuncuysa
        if (targetEntity instanceof PlayerEntity && targetEntity != MinecraftClient.getInstance().player) {
            
            // Eğer configte hayalet (TRANSLUCENT) modu açiksa:
            if (ModConfig.INSTANCE.visualMode == ModConfig.VisualMode.TRANSLUCENT || 
                ModConfig.INSTANCE.visualMode == ModConfig.VisualMode.BOTH) {
                // Return 'false' demek Minecraft'a "bu adam sana gorunmez degil" diyor. Bu sayede Minecraft onu YARI SAYDAM olarak (spectator gibi) ciziyor!
                cir.setReturnValue(false); 
            }
        }
    }

    // Parlayan (Glowing) efekti cizmek icin
    @Inject(method = "isGlowing", at = @At("HEAD"), cancellable = true)
    private void injectGlowing(CallbackInfoReturnable<Boolean> cir) {
        if (!ModConfig.INSTANCE.isEnabled) return;
        
        Entity targetEntity = (Entity) (Object) this;
        
        // Bu kiºinin gercekte gorunmezlik statü etkisi var mi kontrolü
        if (targetEntity instanceof PlayerEntity && targetEntity != MinecraftClient.getInstance().player && targetEntity.isInvisible()) {
            if (ModConfig.INSTANCE.visualMode == ModConfig.VisualMode.GLOWING || 
                ModConfig.INSTANCE.visualMode == ModConfig.VisualMode.BOTH) {
                cir.setReturnValue(true);
            }
        }
    }
}