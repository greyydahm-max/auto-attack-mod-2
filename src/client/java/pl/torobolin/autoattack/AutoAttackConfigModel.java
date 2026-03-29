package pl.torobolin.autoattack;

public class AutoAttackConfigModel {
    public boolean useCustomInterval = false;
    public int interval = 20;
    public boolean useRandomInterval = false;
    public int minRandomInterval = 10;
    public int maxRandomInterval = 40;
    public boolean attackOnlyLivingEntities = true;
    public AutoAttackType autoAttackType = AutoAttackType.ALL;
    public boolean sendToggleMessages = true;
    public boolean showMessagesInActionBar = true;
    public boolean attackPassiveEntities = false;
}
