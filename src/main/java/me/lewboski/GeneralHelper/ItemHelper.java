package me.lewboski.GeneralHelper;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemHelper {
    public static boolean isDiamondPickaxe(@Nullable ItemStack stack) {
        try {
            if (stack != null) {
                Item item = stack.getItem();
                return item == Items.diamond_pickaxe;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error caught for 'isDiamondPickaxe'");
            return false;
        }
    }
}
