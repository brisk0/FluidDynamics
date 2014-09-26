package brisk.fluiddynamics.init;

import brisk.fluiddynamics.block.BlockFD;
import brisk.fluiddynamics.block.BlockPail;
import brisk.fluiddynamics.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by brisk on 24/09/14.
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
    public static final BlockFD blockPail = new BlockPail();

    public static void init() {
        GameRegistry.registerBlock(blockPail, "blockPail");
    }
}
