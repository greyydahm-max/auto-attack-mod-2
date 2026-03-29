package pl.torobolin.autoattack;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            AutoAttackConfigModel config = TorosAutoAttackClient.getConfig();
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Component.translatable("text.autoconfig.toros-auto-attack.title"))
                    .setSavingRunnable(TorosAutoAttackClient::saveConfig);

            ConfigEntryBuilder e = builder.entryBuilder();
            ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));

            general.addEntry(e.startBooleanToggle(Component.translatable("text.autoconfig.toros-auto-attack.option.useCustomInterval"), config.useCustomInterval).setDefaultValue(false).setSaveConsumer(v -> config.useCustomInterval = v).build());
            general.addEntry(e.startIntField(Component.translatable("text.autoconfig.toros-auto-attack.option.interval"), config.interval).setDefaultValue(20).setSaveConsumer(v -> config.interval = v).build());
            general.addEntry(e.startBooleanToggle(Component.translatable("text.autoconfig.toros-auto-attack.option.useRandomInterval"), config.useRandomInterval).setDefaultValue(false).setSaveConsumer(v -> config.useRandomInterval = v).build());
            general.addEntry(e.startIntField(Component.translatable("text.autoconfig.toros-auto-attack.option.minRandomInterval"), config.minRandomInterval).setDefaultValue(10).setSaveConsumer(v -> config.minRandomInterval = v).build());
            general.addEntry(e.startIntField(Component.translatable("text.autoconfig.toros-auto-attack.option.maxRandomInterval"), config.maxRandomInterval).setDefaultValue(40).setSaveConsumer(v -> config.maxRandomInterval = v).build());
            general.addEntry(e.startBooleanToggle(Component.translatable("text.autoconfig.toros-auto-attack.option.attackOnlyLivingEntities"), config.attackOnlyLivingEntities).setDefaultValue(true).setSaveConsumer(v -> config.attackOnlyLivingEntities = v).build());
            general.addEntry(e.startEnumSelector(Component.translatable("text.autoconfig.toros-auto-attack.option.autoAttackType"), AutoAttackType.class, config.autoAttackType).setDefaultValue(AutoAttackType.ALL).setSaveConsumer(v -> config.autoAttackType = v).build());
            general.addEntry(e.startBooleanToggle(Component.translatable("text.autoconfig.toros-auto-attack.option.sendToggleMessages"), config.sendToggleMessages).setDefaultValue(true).setSaveConsumer(v -> config.sendToggleMessages = v).build());
            general.addEntry(e.startBooleanToggle(Component.translatable("text.autoconfig.toros-auto-attack.option.showMessagesInActionBar"), config.showMessagesInActionBar).setDefaultValue(true).setSaveConsumer(v -> config.showMessagesInActionBar = v).build());
            general.addEntry(e.startBooleanToggle(Component.translatable("text.autoconfig.toros-auto-attack.option.attackPassiveEntities"), config.attackPassiveEntities).setDefaultValue(false).setSaveConsumer(v -> config.attackPassiveEntities = v).build());

            return builder.build();
        };
    }
}
