package me.lewboski.GeneralHelper;

import me.lewboski.PlayerStateHelper.AutoSellHelper;
import me.lewboski.Utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class ChatHelper {
    public static void sendChatMessage(String message) {
        Utils.getPlayer().sendChatMessage(message);
    }

    @SubscribeEvent
    public void onChat(@NotNull ClientChatReceivedEvent event) {
        try {
            String message = event.message.getUnformattedText();
            if (message != null && message.contains("Welcome to InvadedLands")) ServerHelper.tryJoinSkyBlock();
            else if (message != null && message.contains("Balance:")) AutoSellHelper.payNovedad(message);
        } catch (Exception e) {
            System.out.println("Error caught for 'onChat'");
        }
    }
}
