package dev.blockeed.managers;

import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ChestManager {

    private static List<ItemStack> items = Arrays.asList(
            ItemStack.builder(Material.DIAMOND_SWORD).build(),
            ItemStack.builder(Material.OAK_PLANKS).amount(16).build(),
            ItemStack.builder(Material.IRON_CHESTPLATE).build(),
            ItemStack.builder(Material.APPLE).amount(2).build(),
            ItemStack.builder(Material.LEATHER_BOOTS).build(),
            ItemStack.builder(Material.IRON_SWORD).build()
    );

    private static Map<Vec, Inventory> chests = new HashMap<>();

    public static void setupChests() {
        GameManager.getMap().getChestPoints().forEach(pos -> {
            Inventory inventory = new Inventory(InventoryType.CHEST_3_ROW, Component.text("Chest"));
            WorldManager.getInstanceContainer().setBlock(pos, Block.CHEST);
            int itemCount = ThreadLocalRandom.current().nextInt(2, 5);
            for (int i = 0; i < itemCount; i++) {
                int slot = ThreadLocalRandom.current().nextInt(27);
                ItemStack itemStack = items.get(ThreadLocalRandom.current().nextInt(items.size()));
                inventory.setItemStack(slot, itemStack);
            }
            chests.put(new Pos(pos.blockX(), pos.blockY(), pos.blockZ()).asVec(), inventory);
        });
    }

    public static boolean isChest(Vec pos) {
        return chests.containsKey(pos);
    }

    public static Inventory getChest(Vec pos) {
        return chests.get(pos);
    }

}
