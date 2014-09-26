package brisk.fluiddynamics.handler;

import brisk.fluiddynamics.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;

/**
 * Created by brisk on 31/08/14.
 */
public class ConfigHandler {
    public static Configuration config;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
        }
        loadConfig();
    }

    private static void loadConfig() {

            //Config List
            boolean overwriteBucket = config.get(Configuration.CATEGORY_GENERAL, "overwriteBucket", false, "Overwrite vanilla bucket with Fluid Dynamics Pail").getBoolean(false);

            if (config.hasChanged()) {
                config.save();
            }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfig();
        }
    }
}
