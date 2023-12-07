package me.lewboski.PlayerStateHelper;

import me.lewboski.GeneralHelper.ChatHelper;
import me.lewboski.GeneralHelper.InventoryHelper;
import me.lewboski.SSMod;
import me.lewboski.Utils.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class MiningHelper {
    public static void startMining() {
        try {
            InventoryHelper.hotbarSelect(0);
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), true);
        } catch (Exception e) {
            System.out.println("Error caught for 'startMining'");
        }
    }

    public static void stopMining() {
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);
    }

    public static void manageState() {
        try {
            long currentTime = System.currentTimeMillis();
            if (currentTime - SSMod.lastCrafted > 25_000) {
                SSMod.currentState = SSMod.PlayerState.CRAFTING;
                SSMod.lastCrafted = currentTime;
                new Thread(() -> {
                    InventoryHelper.blocksToMaterial();
                    ChatHelper.sendChatMessage("/sell all");
                    TimeUtil.sleep();
                    InventoryHelper.closeInventory();
                    SSMod.currentState = SSMod.PlayerState.CRAFTING_DONE;
                }).start();
            }
        } catch (Exception e) {
            System.out.println("Error caught for 'manageState'");
        }

        MiningHelper.startMining();
    }

    public static void toggleCheck() {
        if (SSMod.currentState == SSMod.PlayerState.MINING) {
            MiningHelper.stopMining();
            SSMod.currentState = SSMod.PlayerState.IDLE;
        } else if (SSMod.currentState == SSMod.PlayerState.IDLE) {
            SSMod.currentState = SSMod.PlayerState.MINING;
        }
    }
}
