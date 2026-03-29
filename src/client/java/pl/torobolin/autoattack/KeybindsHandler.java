package pl.torobolin.autoattack;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeybindsHandler {

    private static final String KEY_CATEGORY = "key.category.autoattack";
    private static final String KEY_ATTACK_TOGGLE = "key.autoattack.auto_attack_toggle";
    private static final String MESSAGE_ATTACK_ON = "message.autoattack.auto_attack_on";
    private static final String MESSAGE_ATTACK_OFF = "message.autoattack.auto_attack_off";

    private static KeyMapping autoAttackToggleKey;

    public void registerKeyInputs(AutoAttackHandler handler) {
        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath("autoattack", "autoattack")
        );

        autoAttackToggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                KEY_ATTACK_TOGGLE,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (autoAttackToggleKey.consumeClick()) {
                if (client.player == null) return;

                handler.autoAttack = !handler.autoAttack;

                AutoAttackConfigModel config = TorosAutoAttackClient.getConfig();
                if (config.sendToggleMessages) {
                    Component message = handler.autoAttack
                            ? Component.translatable(MESSAGE_ATTACK_ON)
                            : Component.translatable(MESSAGE_ATTACK_OFF);

                    if (config.showMessagesInActionBar) {
                        client.gui.setOverlayMessage(message, false);
                    } else {
                        client.player.sendSystemMessage(message);
                    }
                }
            }
        });
    }
}
