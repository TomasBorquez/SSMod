package me.lewboski.GeneralHelper;

import me.lewboski.SSMod;
import me.lewboski.Utils.MouseUtil;
import me.lewboski.Utils.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ServerHelper {
    static final private String INVADED_LANDS_IP = "play.invadedlands.net:54.39.38.222";
    static boolean isConnected = false;

    @SubscribeEvent
    public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        isConnectedToggle();
    }

    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        try {
            int lastPlayerStateOrdinal = SSMod.currentState.ordinal();
            ConfigHelper.setLastPlayerState(lastPlayerStateOrdinal);

            isConnectedToggle();
            SSMod.stopAll();
            reconnect();
        } catch (Exception e) {
            System.out.println("Error caught for 'onClientDisconnect'");
        }
    }

    static void reconnect() {
        try {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                ServerData serverData = new ServerData("Reconnect", INVADED_LANDS_IP, false);
                Minecraft.getMinecraft().displayGuiScreen(new GuiConnecting(new GuiMainMenu(), Minecraft.getMinecraft(), serverData));
            });
        } catch (Exception e) {
            System.out.println("Error caught for 'reconnect'");
        }
    }

    public static void tryJoinSkyBlock() {
        try {
            new Thread(() -> {
                TimeUtil.longSleep();
                joinSkyBlock();
            }).start();
        } catch (Exception e) {
            System.out.println("Error caught for 'onClientConnect'");
            new Thread(() -> {
                TimeUtil.customSleep(30_000);
                joinSkyBlock();
            }).start();
        }
    }

    public static void joinSkyBlock() {
        try {
            InventoryHelper.hotbarSelect(0);
            TimeUtil.sleep();
            MouseUtil.clickOnce("right");
            TimeUtil.customSleep(5_000); // Set to 30 later
            ChestHelper.clickOnSlot(13, "medium");
            TimeUtil.customSleep(5_000);
            int lastPlayerStateOrdinal = Integer.parseInt(ConfigHelper.lastPlayerState());
            SSMod.currentState = SSMod.PlayerState.values()[lastPlayerStateOrdinal];
        } catch (NumberFormatException e) {
            System.out.println("Error caught for 'joinSkyBlock'");
        }
    }

    private static void isConnectedToggle() {
        isConnected = !isConnected;
    }
}
