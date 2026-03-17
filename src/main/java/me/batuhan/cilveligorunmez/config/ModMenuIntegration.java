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

            ConfigCategory general = builder.getOrCreateCategory(Text.literal("Menü"));
            
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Ana Şalter"), ModConfig.INSTANCE.isEnabled)
                    .setDefaultValue(false)
                    .setTooltip(Text.literal("Cilveli Priv'i kökten aşıp kapatmanı sağlar."))
                    .setSaveConsumer(newValue -> ModConfig.INSTANCE.isEnabled = newValue)
                    .build());
                    
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Sessiz Mod (Chat/Actionbar)"), ModConfig.INSTANCE.stealthMode)
                    .setDefaultValue(false)
                    .setTooltip(Text.literal("F4'e basıp modu açıp kapatırken oyunda bildirim çıksın mı? (yayıncılar açabilir)"))
                    .setSaveConsumer(newValue -> ModConfig.INSTANCE.stealthMode = newValue)
                    .build());

            general.addEntry(entryBuilder.startEnumSelector(Text.literal("Adamlar Nasıl Gözüksün"), ModConfig.VisualMode.class, ModConfig.INSTANCE.visualMode)
                    .setDefaultValue(ModConfig.VisualMode.BOTH)
                    .setTooltip(Text.literal("Görünmez elemanların çizilme biçimi:\nTRANSLUCENT: Yarı Saydam\nGLOWING: Dış Hat Parlaması\nBOTH: İkisi Birden"))
                    .setSaveConsumer(newValue -> ModConfig.INSTANCE.visualMode = newValue)
                    .build());

            return builder.build();
        };
    }
}