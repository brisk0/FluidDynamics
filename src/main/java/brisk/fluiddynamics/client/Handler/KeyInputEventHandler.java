package brisk.fluiddynamics.client.Handler;

import brisk.fluiddynamics.client.settings.Keybindings;
import brisk.fluiddynamics.reference.Key;
import brisk.fluiddynamics.util.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;


/**
 * Created by brisk on 24/09/14.
 */
public class KeyInputEventHandler {

    private static Key getPressedKeyBinding() {
        if (Keybindings.charge.isPressed()) {
            return Key.CHARGE;
        } else if (Keybindings.release.isPressed()) {
            return Key.RELEASE;
        }
        return Key.UNKNOWN;
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event) {

    }
}
