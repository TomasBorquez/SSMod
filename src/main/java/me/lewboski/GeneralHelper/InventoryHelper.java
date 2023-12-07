package me.lewboski.GeneralHelper;

import me.lewboski.Utils.MouseUtil;
import me.lewboski.Utils.TimeUtil;
import me.lewboski.Utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class InventoryHelper {
    public static void blocksToMaterial() {
        try {
            openInventory();
            TimeUtil.sleep();
            ArrayList<Integer> blocks = getBlocksFromInventory();
            int x = MouseUtil.getConvertedX(285);
            int y = 109;
            int blocksIndex = 0;
            for (int i = 0; i < 9; i++) {
                if (blocksIndex == blocks.size()) return;
                int block = blocks.get(blocksIndex);
                if (block == i) {
                    blocksIndex++;
                    Mouse.setCursorPosition(x, y);
                    TimeUtil.sleep();
                    craftIngot();
                }

                x += 35;
            }

            x = MouseUtil.getConvertedX(285);
            y = 229;
            for (int i = 0; i < 9 * 3; i++) {
                if (i == 9 || i == 18) {
                    x = MouseUtil.getConvertedX(285);
                    y -= 40;
                }
                if (blocksIndex == blocks.size()) return;
                int block = blocks.get(blocksIndex);
                if (block == i + 9) {
                    blocksIndex++;
                    Mouse.setCursorPosition(x, y);
                    TimeUtil.sleep();
                    craftIngot();
                }

                x += 35;
            }
        } catch (Exception ex) {
            System.out.println("Error caught for 'craftToIngots'");
        }
    }

    public static void craftIngot() {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(0);

            int firstCraftingSlotX = MouseUtil.getConvertedX(443);
            int firstCraftingSlotY = 333;

            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            TimeUtil.sleep();
            Mouse.setCursorPosition(firstCraftingSlotX, firstCraftingSlotY);

            TimeUtil.sleep();
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            TimeUtil.sleep();
            int resultSlotX = MouseUtil.getConvertedX(555);
            int resultSlotY = 312;
            robot.keyPress(KeyEvent.VK_SHIFT);
            Mouse.setCursorPosition(resultSlotX, resultSlotY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            TimeUtil.sleep();
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (Exception e) {
            System.out.println("Error caught for 'craftIngot'");
        }
    }

    public static @NotNull ArrayList<Integer> getBlocksFromInventory() {
        try {
            EntityPlayerSP player = Utils.getPlayer();
            ArrayList<Integer> blocks = new ArrayList<>();

            for (int i = 1; i < player.inventory.mainInventory.length; i++) {
                ItemStack stack = player.inventory.mainInventory[i];

                if (stack != null) {
                    if (stack.getItem() instanceof ItemBlock) {
                        ItemBlock itemBlock = (ItemBlock) stack.getItem();
                        Block block = itemBlock.getBlock();

                        if (block.equals(Blocks.diamond_block)) blocks.add(i);
                        else if (block.equals(Blocks.emerald_block)) blocks.add(i);
                        else if (block.equals(Blocks.gold_block)) blocks.add(i);
                        else if (block.equals(Blocks.iron_block)) blocks.add(i);
                        else if (block.equals(Blocks.lapis_block)) blocks.add(i);
                        else if (block.equals(Blocks.redstone_block)) blocks.add(i);
                    }
                }
            }

            return blocks;
        } catch (Exception ex) {
            System.out.println("Error caught for 'getBlocksFromInventory'");
            return new ArrayList<>();
        }
    }

    public static void openInventory() {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(0);

            robot.keyPress(KeyEvent.VK_PERIOD);
            TimeUtil.sleep();
            robot.keyRelease(KeyEvent.VK_PERIOD);
        } catch (Exception e) {
            System.out.println("Error caught for 'openInventory'");
        }
    }

    public static void closeInventory() {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(0);

            robot.keyPress(KeyEvent.VK_ESCAPE);
            TimeUtil.sleep();
            robot.keyRelease(KeyEvent.VK_ESCAPE);
        } catch (Exception e) {
            System.out.println("Error caught for 'closeInventory'");
        }
    }

    public static void hotbarSelect(int slot) {
        try {
            EntityPlayerSP player = Utils.getPlayer();
            player.inventory.currentItem = slot;
        } catch (Exception e) {
            System.out.println("Error caught for 'hotbarSelect'");
        }
    }

    public static @Nullable ItemStack checkHotbarItemAt(int slot) {
        try {
            EntityPlayerSP player = Utils.getPlayer();
            InventoryPlayer inventory = player.inventory;
            return inventory.getStackInSlot(slot);
        } catch (Exception e) {
            System.out.println("Error caught for 'checkHotbarItemAt'");
            return null;
        }
    }
}
