package me.lewboski;

import me.lewboski.GeneralHelper.*;
import me.lewboski.PlayerStateHelper.AutoSellHelper;
import me.lewboski.PlayerStateHelper.CroppingHelper;
import me.lewboski.PlayerStateHelper.MiningHelper;
import me.lewboski.UIHelpers.UIScreen;
import me.lewboski.Utils.TimeUtil;
import me.lewboski.Utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Mod(modid = SSMod.MODID, name = SSMod.NAME, version = SSMod.VERSION)
public class SSMod {
    public static final String MODID = "SSMod";
    public static final String NAME = "Skyblock Scripts";
    public static final String VERSION = "2.0";
    public static long lastCrafted;
    public static PlayerState currentState = PlayerState.IDLE;

    public enum PlayerState {
        IDLE,
        ATTACKING_ENTITY,
        MINING,
        CROPPING,
        CRAFTING,
        CRAFTING_DONE,
        GRABBING_PICKAXE,
        GRABBING_PICKAXE_DONE,
        CAPTCHA,
        CAPTCHA_DONE,
        AUTOSELL
    }

    public static PlayerState lastPlayerState = PlayerState.IDLE;
    public static final KeyBinding rightShift = new KeyBinding("Description", Keyboard.KEY_RSHIFT, "Category");

    @Mod.EventHandler
    public void preInit(@NotNull FMLPreInitializationEvent event) {
        new ConfigHelper(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println(NAME + " is ready for hacking");
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ServerHelper());
        MinecraftForge.EVENT_BUS.register(new ChatHelper());
        ClientRegistry.registerKeyBinding(rightShift);

        long time = System.currentTimeMillis() ;
        System.out.println(time);
        System.out.println("--------------------------");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Minecraft is shutting down!");
            ConfigHelper.setLastPlayerState(PlayerState.IDLE.ordinal());
        }));
    }

    @SubscribeEvent
    public void onItemBreak(@NotNull PlayerDestroyItemEvent event) {
        ItemStack brokenItem = event.original;
        if (brokenItem != null) {
            if (ItemHelper.isDiamondPickaxe(brokenItem)) {
                currentState = SSMod.PlayerState.GRABBING_PICKAXE;
                new Thread(() -> {
                    ChatHelper.sendChatMessage("/pv 1");
                    TimeUtil.longSleep();

                    ChestHelper.replacePickaxe();
                    TimeUtil.sleep();
                    currentState = SSMod.PlayerState.GRABBING_PICKAXE_DONE;
                }).start();
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(@NotNull GuiOpenEvent event) {
        if (event.gui instanceof GuiChest) {
            try {
                new Thread(() -> {
                    lastPlayerState = currentState;
                    GuiChest chestGUI = (GuiChest) event.gui;
                    TimeUtil.sleep();
                    IInventory chestInventory = ((ContainerChest) chestGUI.inventorySlots).getLowerChestInventory();

                    if (chestInventory.hasCustomName()) {
                        TimeUtil.sleep();
                        IChatComponent chestNameComponent = chestInventory.getDisplayName();
                        String chestName = chestNameComponent.getUnformattedText();
                        String glassColor = Utils.isCaptcha(chestName);
                        System.out.println("ChestName: " + chestName);

                        if (glassColor != null && !glassColor.isEmpty()) {
                            currentState = PlayerState.CAPTCHA;
                            int slotIndex = ChestHelper.searchForBlock(chestInventory, glassColor);
                            if (slotIndex >= 0) ChestHelper.clickOnSlot(slotIndex, "small");
                            currentState = PlayerState.CAPTCHA_DONE;
                        }
                    }
                }).start();
            } catch (Exception e) {
                currentState = PlayerState.CAPTCHA_DONE;
                System.out.println("Error caught for 'onGuiOpen'");
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.@NotNull PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        switch (currentState) {
            case MINING:
                MiningHelper.manageState();
                break;
            case CROPPING:
                CroppingHelper.manageState();
                break;
            case CAPTCHA:
                MiningHelper.stopMining();
                break;
            case CAPTCHA_DONE:
                currentState = lastPlayerState;
                break;
            case CRAFTING:
                MiningHelper.stopMining();
                break;
            case CRAFTING_DONE:
                currentState = PlayerState.MINING;
                break;
            case GRABBING_PICKAXE:
                MiningHelper.stopMining();
                break;
            case GRABBING_PICKAXE_DONE:
                currentState = PlayerState.MINING;
                break;
            case AUTOSELL:
                AutoSellHelper.manageState();
                break;
            default:
                break;
        }
    }

    static public void stopAll() {
        MiningHelper.stopMining();
        CroppingHelper.stopCropping();
        SSMod.currentState = SSMod.PlayerState.IDLE;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.@NotNull ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (rightShift.isPressed()) Minecraft.getMinecraft().displayGuiScreen(new UIScreen());
    }
}
