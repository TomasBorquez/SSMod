package me.lewboski.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Utils {
    private final static Random random = new Random();
    private static final @NotNull Map<String, String> CAPTCHA_MAP;

    static {
        CAPTCHA_MAP = new HashMap<>();
        CAPTCHA_MAP.put("Brown", "tile.thinStainedGlass.brown");
        CAPTCHA_MAP.put("Black", "tile.thinStainedGlass.black");
        CAPTCHA_MAP.put("Blue", "tile.thinStainedGlass.blue");
        CAPTCHA_MAP.put("Cyan", "tile.thinStainedGlass.cyan");
        CAPTCHA_MAP.put("Green", "tile.thinStainedGlass.green");
        CAPTCHA_MAP.put("Red", "tile.thinStainedGlass.red");
        CAPTCHA_MAP.put("Gray", "tile.thinStainedGlass.gray");
        CAPTCHA_MAP.put("Orange", "tile.thinStainedGlass.orange");
        CAPTCHA_MAP.put("Magenta", "tile.thinStainedGlass.magenta");
        CAPTCHA_MAP.put("Light blue", "tile.thinStainedGlass.light_blue");
        CAPTCHA_MAP.put("Light gray", "tile.thinStainedGlass.light_gray");
        CAPTCHA_MAP.put("Lime", "tile.thinStainedGlass.lime");
        CAPTCHA_MAP.put("Yellow", "tile.thinStainedGlass.yellow");
        CAPTCHA_MAP.put("Purple", "tile.thinStainedGlass.purple");
        CAPTCHA_MAP.put("Pink", "tile.thinStainedGlass.pink");
        CAPTCHA_MAP.put("White", "tile.thinStainedGlass.white");
    }

    public static @Nullable String isCaptcha(@NotNull String chestName) {
        for (String key : CAPTCHA_MAP.keySet()) {
            if (chestName.toLowerCase().contains(key.toLowerCase())) return CAPTCHA_MAP.get(key);
        }
        return null;
    }

    public static EntityPlayerSP getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static int randomNumberRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than Min");
        }

        return random.nextInt((max - min) + 1) + min;
    }
}
