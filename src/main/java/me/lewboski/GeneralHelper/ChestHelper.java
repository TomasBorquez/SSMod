package me.lewboski.GeneralHelper;

import me.lewboski.Utils.TimeUtil;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.event.InputEvent;

public class ChestHelper {
    public static int searchForBlock(@NotNull IInventory chestInventory, String glassColor) {
        try {
            int size = chestInventory.getSizeInventory();
            for (int slot = 0; slot < size; slot++) {
                ItemStack stack = chestInventory.getStackInSlot(slot);

                if (stack != null && Block.getBlockFromItem(stack.getItem()) == Blocks.stained_glass_pane) {
                    String blockName = stack.getUnlocalizedName();
                    if (blockName.equals(glassColor)) return slot;
                }
            }
            return -1;
        } catch (Exception ex) {
            System.out.println("Error caught for 'searchForBlock'");
            return -1;
        }
    }


    public static void clickOnSlot(int slotIndex, @NotNull String chestSize) {
        try {
            int x = 285;
            switch (chestSize) {
                case "large": {
                    int y = 420;
                    for (int i = 0; i < 54; i++) {
                        if (i == 9 || i == 18 || i == 27 || i == 36 || i == 45) {
                            x = 285;
                            y -= 35;
                        }
                        if (i == slotIndex) {
                            clickAt(x, y);
                            return;
                        }
                        x += 35;
                    }
                    break;
                }
                case "medium": {
                    int y = 394;
                    for (int i = 0; i < 45; i++) {
                        if (i == 9 || i == 18 || i == 27 || i == 36) {
                            x = 285;
                            y -= 35;
                        }
                        if (i == slotIndex) {
                            clickAt(x, y);
                            return;
                        }
                        x += 35;
                    }
                    break;
                }
                case "small": {
                    int y = 350;
                    for (int i = 0; i < 26; i++) {
                        if (i == 9 || i == 18) {
                            x = 285;
                            y -= 35;
                        }
                        if (i == slotIndex) {
                            clickAt(x, y);
                            return;
                        }
                        x += 35;
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error caught for 'clickOnSlot'");
        }
    }

    public static void clickAt(int x, int y) {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(0);
            Mouse.setCursorPosition(x, y);
            TimeUtil.sleep();
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            TimeUtil.sleep();
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (Exception e) {
            System.out.println("Error caught for 'craftIngot'");
        }
    }

    public static void clickAtAndGrab(int x, int y) {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(0);
            Mouse.setCursorPosition(x, y);
            TimeUtil.sleep();
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            TimeUtil.sleep();
        } catch (Exception e) {
            System.out.println("Error caught for 'craftIngot'");
        }
    }

    public static void replacePickaxe() {
        try {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiChest) {
                GuiChest chestGUI = (GuiChest) Minecraft.getMinecraft().currentScreen;
                TimeUtil.sleep();
                IInventory chestInventory = ((ContainerChest) chestGUI.inventorySlots).getLowerChestInventory();
                int numberOfSlots = chestInventory.getSizeInventory();

                if (numberOfSlots == 54) {
                    System.out.println("This is a double chest");
                }

                int pickaxeSlot = checkItemsChest(chestInventory);
                if (pickaxeSlot >= 0) {
                    grabItemToInventory(pickaxeSlot);
                    TimeUtil.sleep();
                    takeItemToHotbar();
                    TimeUtil.sleep();
                    InventoryHelper.closeInventory();
                }
            }
        } catch (Exception e) {
            System.out.println("Error caught for 'replacePickaxe'");
        }
    }

    public static void grabItemToInventory(int slot) {
        try {
            TimeUtil.sleep();
            int x = 285;
            int y = 420;
            for (int i = 0; i < 54; i++) {
                if (i == 9 || i == 18 || i == 27 || i == 36 || i == 45) {
                    x = 285;
                    y -= 35;
                }
                if (i == slot) {
                    clickAtAndGrab(x, y);
                    return;
                }
                x += 35;
            }
        } catch (Exception e) {
            System.out.println("Error caught for 'clickOnSlot'");
        }
    }

    public static int checkItemsChest(@NotNull IInventory chestInventory) {
        try {
            for (int i = 0; i < chestInventory.getSizeInventory(); i++) {
                ItemStack stack = chestInventory.getStackInSlot(i);
                if (ItemHelper.isDiamondPickaxe(stack)) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            System.out.println("Error caught for 'checkItemsChest'");
            return -1;
        }
    }

    public static void takeItemToHotbar() {
        try {
            final int x = 285;
            final int y = 52;
            Robot robot = new Robot();
            robot.setAutoDelay(0);
            Mouse.setCursorPosition(x, y);
            TimeUtil.longSleep();
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (Exception e) {
            System.out.println("Error caught for 'takeItemToHotbar'");
        }
    }
}
