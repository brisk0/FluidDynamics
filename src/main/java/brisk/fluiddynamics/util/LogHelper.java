package brisk.fluiddynamics.util;

import brisk.fluiddynamics.reference.Reference;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

/**
 * Created by brisk on 22/09/14.
 */
public class LogHelper {
    public static void log(Level level, Object object)
    {
        FMLLog.log(Reference.MOD_NAME, level, String.valueOf(object));
    }

    public static void log(Object object)
    {
        FMLLog.log(Reference.MOD_NAME, Level.INFO, String.valueOf(object));
    }

}
