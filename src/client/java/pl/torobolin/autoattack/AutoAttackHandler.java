package pl.torobolin.autoattack;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Random;

public class AutoAttackHandler {

    private AutoAttackConfigModel config;
    public boolean autoAttack = false;
    private int ticks = 0;
    private int randomTicks = 0;
    private int cooldownTicks = 0;
    private final Random random = new Random();

    public void handleAttack() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            this.config = TorosAutoAttackClient.getConfig();

            LocalPlayer player = client.player;
            if (player == null || !autoAttack) return;

            // Validate interval config
            if (config.useRandomInterval && config.useCustomInterval) {
                client.gui.setOverlayMessage(
        Component.literal("You can only have one interval type set at a time"), false);
                return;
            }

            // Determine cooldown length
            if (config.useCustomInterval) {
                cooldownTicks = config.interval;
            } else if (config.useRandomInterval) {
                if (randomTicks <= 0) {
                    randomTicks = random.nextInt(config.minRandomInterval, config.maxRandomInterval);
                }
                cooldownTicks = randomTicks;
            } else {
                // Default: attack when cooldown is full
                cooldownTicks = 0;
            }

            ticks++;

            // Check if we should attack this tick
            boolean shouldAttack;
            if (cooldownTicks > 0) {
                shouldAttack = ticks >= cooldownTicks;
                if (shouldAttack) {
                    ticks = 0;
                    randomTicks = 0;
                }
            } else {
                // No custom interval - attack when strength scale is full
                shouldAttack = player.getAttackStrengthScale(0.0f) >= 1.0f;
            }

            if (!shouldAttack) return;

            // Check crosshair target
            HitResult hit = client.hitResult;
            if (hit == null || hit.getType() != HitResult.Type.ENTITY) return;

            Entity entity = ((EntityHitResult) hit).getEntity();

            // Living entity filter
            if (config.attackOnlyLivingEntities && !(entity instanceof LivingEntity)) return;

            // Passive entity filter
            if (!config.attackPassiveEntities && entity instanceof Animal) return;

            // Weapon type filter
            if (!isCorrectWeapon(player, config.autoAttackType)) return;

            // Perform attack
            client.gameMode.attack(player, entity);
            player.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
        });
    }

    private boolean isCorrectWeapon(LocalPlayer player, AutoAttackType type) {
        net.minecraft.world.item.Item heldItem = player.getMainHandItem().getItem();
        return switch (type) {
            case SWORD -> AutoAttackItemsList.swordList.contains(heldItem);
            case AXE -> AutoAttackItemsList.axeList.contains(heldItem);
            case BOTH -> AutoAttackItemsList.swordList.contains(heldItem)
                    || AutoAttackItemsList.axeList.contains(heldItem);
            case ALL -> true;
        };
    }
}
