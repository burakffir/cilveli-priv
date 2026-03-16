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

            ConfigCategory general = builder.getOrCreateCategory(Text.literal("Genel Seçenekler"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Özellik Aktif"), ModConfig.INSTANCE.isEnabled)
                    .setDefaultValue(false)
                    .setTooltip(Text.literal("Modu tamamen açıp/kapatır."))
                    .setSaveConsumer(newValue -> ModConfig.INSTANCE.isEnabled = newValue)
                    .build());
                    
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Sessiz Mod (Gizli)"), ModConfig.INSTANCE.stealthMode)
                    .setDefaultValue(false)
                    .setTooltip(Text.literal("Aktif edildiğinde F4'e basınca ekrana 'CILVELI' yazısı gelmez. Yayında vs. belli etmemek için."))
                    .setSaveConsumer(newValue -> ModConfig.INSTANCE.stealthMode = newValue)
                    .build());

            general.addEntry(entryBuilder.startEnumSelector(Text.literal("Gösterim Tarzı"), ModConfig.VisualMode.class, ModConfig.INSTANCE.visualMode)
                    .setEnumNameProvider(enumValue -> Text.literal(((ModConfig.VisualMode) enumValue).getLabel()))
                    .setDefaultValue(ModConfig.VisualMode.TRANSLUCENT)
                    .setTooltip(Text.literal("Gösterim biçimini belirler."))
                    .setSaveConsumer(newValue -> ModConfig.INSTANCE.visualMode = newValue)
                    .build());

            return builder.build();
        };
    }
}