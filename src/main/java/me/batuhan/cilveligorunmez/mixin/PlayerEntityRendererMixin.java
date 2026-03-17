package me.batuhan.cilveligorunmez.mixin;

import me.batuhan.cilveligorunmez.config.ModConfig;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    @Inject(method = "render*", at = @At("HEAD"))
    private void renderGhostMode(AbstractClientPlayerEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int renderLight, CallbackInfo ci) {
        // Bu mixin aslında sadece bir örnek.
        // EntityRenderer icinde isTranslucent vesaire metotları vardir.
        // Fabric API ve vanilla kodunda isInvisibleTo zaten yarÄ± saydam yapmayi handle edebilir eger
        // seyirci modundaymis gibi davranirsak ama renderPlayer'da ekstra kontrol koyabiliriz isterseniz.
    }
}