package me.batuhan.cilveligorunmez;

import me.batuhan.cilveligorunmez.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class CilveliGorunmez implements ClientModInitializer {

    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        ModConfig.load();

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Cilveli Priv Aç/Kapat", 
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_F4, 
                "Cilveli Modlar" 
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                ModConfig.INSTANCE.isEnabled = !ModConfig.INSTANCE.isEnabled;
                ModConfig.save();

                if (client.player != null && !ModConfig.INSTANCE.stealthMode) {
                    if (ModConfig.INSTANCE.isEnabled) {
                        client.player.sendMessage(
                                Text.literal("CİLVELİ > ").formatted(Formatting.WHITE)
                                .append(Text.literal("artık görünmezleri görüyorsun").formatted(Formatting.GREEN)), 
                                true
                        );
                    } else {
                        client.player.sendMessage(
                                Text.literal("CİLVELİ > ").formatted(Formatting.WHITE)
                                .append(Text.literal("artık görünmezleri görmüyorsun").formatted(Formatting.RED)), 
                                true
                        );
                    }
                }
            }
        });
    }
}