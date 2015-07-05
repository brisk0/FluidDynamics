package brisk.fluiddynamics.init;

import brisk.fluiddynamics.item.ItemFD;
import brisk.fluiddynamics.item.ItemPail;
import brisk.fluiddynamics.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.ItemFluidContainer;

/**
 * Created by brisk on 22/09/14.
 */

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
    public static final Item pail = new ItemPail();

    public static void init()
    {
        GameRegistry.registerItem(pail, "Pail");
    }
}
