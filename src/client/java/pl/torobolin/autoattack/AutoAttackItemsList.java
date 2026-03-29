package pl.torobolin.autoattack;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

public class AutoAttackItemsList {
    public static final List<Item> swordList = List.of(
            Items.WOODEN_SWORD,
            Items.STONE_SWORD,
            Items.IRON_SWORD,
            Items.GOLDEN_SWORD,
            Items.DIAMOND_SWORD,
            Items.NETHERITE_SWORD
    );

    public static final List<Item> axeList = List.of(
            Items.WOODEN_AXE,
            Items.STONE_AXE,
            Items.IRON_AXE,
            Items.GOLDEN_AXE,
            Items.DIAMOND_AXE,
            Items.NETHERITE_AXE
    );
}
