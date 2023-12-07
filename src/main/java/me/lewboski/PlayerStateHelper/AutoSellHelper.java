package me.lewboski.PlayerStateHelper;

import me.lewboski.GeneralHelper.ChatHelper;
import me.lewboski.SSMod;
import me.lewboski.Utils.TimeUtil;
import me.lewboski.Utils.Utils;

public class AutoSellHelper {
    private static long lastSold;
    private static long currentTime;
    private static long randomNumber;

    public static void manageState() {
        currentTime = System.currentTimeMillis();
        if (isTimeToSell(currentTime)) sell();
    }

    public static boolean isTimeToSell(long _currentTime) {
        return _currentTime - lastSold > TimeUtil.ONE_SECOND * 5 + randomNumber;
    }

    public static void sell() {
        try {
            randomNumber = Utils.randomNumberRange(0, 10_000);
            ChatHelper.sendChatMessage("/sell all");
            lastSold = currentTime;
        } catch (Exception e) {
            System.out.println("Error caught for 'sell'");
        }
    }

    public static void payNovedad(String message) {
        String amount = message.replace("Balance: $", "");

        String[] parts = amount.split("\\.");

        String integerPart = parts[0];
        String fractionalPart = parts.length > 1 ? parts[1] : "00";

        String cleanedIntegerPart = integerPart.replace(",", "_");

        String cleanedBalance = cleanedIntegerPart + "." + fractionalPart;

        System.out.println(cleanedBalance);

        ChatHelper.sendChatMessage("/pay Novedad " + cleanedBalance);
    }

    public static void toggleCheck() {
        if (SSMod.currentState == SSMod.PlayerState.AUTOSELL) {
            SSMod.currentState = SSMod.PlayerState.IDLE;
        } else if (SSMod.currentState == SSMod.PlayerState.IDLE) {
            SSMod.currentState = SSMod.PlayerState.AUTOSELL;
        }
    }
}
