package me.batuhan.cilveligorunmez.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("Cilveli Priv Ayarları"));

            builder.setSavingRunnable(ModConfig::save);
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            // Sadece stealth modunu buraya biraktim vs
            ConfigCategory general = builder.getOrCreateCategory(Text.literal("Genel Seçenekler"));
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Sessiz Mod (Gizlilik)"), ModConfig.INSTANCE.stealthMode)
                    .setDefaultValue(false)
                    .setTooltip(Text.literal("F4'e basınca sohbete/action bar'a renkli bildirim düşmez. Yayın ekranında mod gizlenir."))
                    .setSaveConsumer(newValue -> ModConfig.INSTANCE.stealthMode = newValue)
                    .build());

            general.addEntry(entryBuilder.startEnumSelector(Text.literal("Görsel İşleme (Render) Modu"), ModConfig.VisualMode.class, ModConfig.INSTANCE.visualMode)
                    .setDefaultValue(ModConfig.VisualMode.BOTH)
                    .setTooltip(Text.literal("Görünmez oyuncuların nasıl işleneceğini seçer.\nTRANSLUCENT: Yarı Saydam\nGLOWING: Parlayan Hatlar\nBOTH: İkisi Birden"))
                    .setSaveConsumer(newValue -> ModConfig.INSTANCE.visualMode = newValue)
                    .build());

            // 2. Sekme Onemli
            ConfigCategory onemli = builder.getOrCreateCategory(Text.literal("Önemli!"));
            onemli.addEntry(entryBuilder.startBooleanToggle(Text.literal("Ana Şalter (Aktif/Pasif)"), ModConfig.INSTANCE.isEnabled)
                    .setDefaultValue(false)
                    .setTooltip(Text.literal("Modu tamamen açıp/kapatır."))
                    .setSaveConsumer(newValue -> ModConfig.INSTANCE.isEnabled = newValue)
                    .build());

            return builder.build();
        };
    }
}