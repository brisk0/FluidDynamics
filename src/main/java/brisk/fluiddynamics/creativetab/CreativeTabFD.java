package brisk.fluiddynamics.creativetab;

import brisk.fluiddynamics.init.ModItems;
import brisk.fluiddynamics.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by brisk on 24/09/14.
 */
public class CreativeTabFD {
    @SideOnly(Side.CLIENT)
    public static final CreativeTabs FD_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase()) {
        @Override
        public Item getTabIconItem() {
            return ModItems.pail;
        }
    };
}
