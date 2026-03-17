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
        if (!ModConfig.INSTANCE.isEnabled) return;
        
        // Eger Minecraft'in varsayilan yarı saydam islemesini guclendirmek istersen buraya alfa değerleriyle MatrixStack / RenderSystem uzerinde manipülasyon yapilabilir.
        // Fabric API ve vanilla kodunda isInvisibleTo(false) yapmak zaten seyirci modundaymis gibi (0.15f alpha) yari saydamlik sagladigindan dolayı genellikle buraya dokunmadan çalışıyor.
        // Ama görsel estetiği arttırmak istersen OpenGL bacakları buradan yönetilebilir.
    }
}