package pl.torobolin.autoattack;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public class TorosAutoAttackClient implements ClientModInitializer {

    private static AutoAttackConfigModel config;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(AutoAttackConfigModel.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(AutoAttackConfigModel.class).getConfig();

        AutoAttackHandler handler = new AutoAttackHandler();
        new KeybindsHandler().registerKeyInputs(handler);
        handler.handleAttack();
    }

    public static AutoAttackConfigModel getConfig() {
        return config;
    }
}
