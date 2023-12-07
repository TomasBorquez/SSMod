package me.lewboski.Utils;

import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Collection;

public class MouseUtil {
    public static void clickOnce(@NotNull String button) {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(0);
            if (button.contains("left")) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            } else if (button.contains("right")) {
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
            }
        } catch (Exception e) {
            System.out.println("Error caught for 'clickOnce'");
        }
    }

    public static int getConvertedX(int x) {
        try {
            Collection<PotionEffect> effects = Utils.getPlayer().getActivePotionEffects();

            if (effects.isEmpty()) {
                return x;
            } else {
                return x + 120;
            }
        } catch (Exception e) {
            System.out.println("Error caught for 'getConvertedX'");
            return x;
        }
    }
}
