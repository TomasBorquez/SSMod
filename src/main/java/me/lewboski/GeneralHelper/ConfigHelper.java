package me.lewboski.GeneralHelper;
import net.minecraftforge.common.config.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ConfigHelper {
    static private @Nullable Configuration config = null;
    static private String lastPlayerState;

    public ConfigHelper(@NotNull File configFile) {
        try {
            config = new Configuration(configFile);
            this.load();
        } catch (Exception e) {
            System.out.println("Error caught for 'ConfigHelper'");
        }
    }

    public void load() {
        try {
            assert config != null;
            config.load();
            lastPlayerState = config.getString("lastPlayerState", Configuration.CATEGORY_GENERAL, "0", "The state on what the player last was");
            if (config.hasChanged()) {
                config.save();
            }
        } catch (Exception e) {
            System.out.println("Error caught for 'load'");

        }
    }

    static public void setLastPlayerState(int lastPlayerStateOrdinal) {
        try {
            assert config != null;
            lastPlayerState = Integer.toString(lastPlayerStateOrdinal );
            config.get(Configuration.CATEGORY_GENERAL, "lastPlayerState", "0").set(lastPlayerState);
            if (config.hasChanged()) {
                config.save();
            }
        } catch (Exception e) {
            System.out.println("Error caught for 'setLastPlayerState'");
        }
    }

    static public String lastPlayerState() {
        return lastPlayerState;
    }
}
