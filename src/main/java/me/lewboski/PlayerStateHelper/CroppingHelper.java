package me.lewboski.PlayerStateHelper;

import me.lewboski.GeneralHelper.ChatHelper;

import me.lewboski.GeneralHelper.InventoryHelper;
import me.lewboski.SSMod;
import me.lewboski.Utils.MouseUtil;
import me.lewboski.Utils.TimeUtil;
import me.lewboski.Utils.Utils;

public class CroppingHelper {
    static long lastPayed;
    static boolean isSelling = false;
    static boolean click = false;

    public static void startCropping() {
        try {
            InventoryHelper.hotbarSelect(0);

            click = true;

            new Thread(() -> {
                while (click) {
                    int delayMillis = Utils.randomNumberRange(5, 15) * 10;
                    System.out.println(delayMillis);

                    TimeUtil.customSleep(delayMillis);
                    MouseUtil.clickOnce("right");
                }
            }).start();
        } catch (Exception e) {
            System.out.println("Error caught for 'startCropping'");
        }
    }

    public static void stopCropping() {
        click = false;
    }

    public static void manageState() {
        try {
            long currentTime = System.currentTimeMillis();
            AutoSellHelper.manageState();
            if (!click) startCropping();
            if (currentTime - lastPayed > TimeUtil.ONE_HOUR) {
                lastPayed = currentTime;
                ChatHelper.sendChatMessage("/bal");
            }
        } catch (Exception e) {
            isSelling = false;
            System.out.println("Error caught for 'manageState'");
        }
    }

    public static void toggleCheck() {
        if (SSMod.currentState == SSMod.PlayerState.CROPPING) {
            stopCropping();
            SSMod.currentState = SSMod.PlayerState.IDLE;
        } else if (SSMod.currentState == SSMod.PlayerState.IDLE) {
            SSMod.currentState = SSMod.PlayerState.CROPPING;
        }
    }
}
