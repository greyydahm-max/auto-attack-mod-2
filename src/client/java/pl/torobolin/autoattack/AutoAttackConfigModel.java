package pl.torobolin.autoattack;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "toros-auto-attack")
public class AutoAttackConfigModel implements ConfigData {
    public boolean useCustomInterval = false;
    public int interval = 20;
    public boolean useRandomInterval = false;
    public int minRandomInterval = 10;
    public int maxRandomInterval = 40;
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean attackOnlyLivingEntities = true;
    @ConfigEntry.Gui.Tooltip(count = 4)
    public AutoAttackType autoAttackType = AutoAttackType.ALL;
    public boolean sendToggleMessages = true;
    public boolean showMessagesInActionBar = true;
    public boolean attackPassiveEntities = false;
}
