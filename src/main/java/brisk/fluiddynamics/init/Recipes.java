package brisk.fluiddynamics.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by brisk on 24/09/14.
 */
public class Recipes {
    public static void init() {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.blockPail), " B ", "B B", " B ", 'B', new ItemStack(ModItems.pail));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pail), new ItemStack(Items.bucket), new ItemStack(Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pail), "I", "B", 'I', "ingotIron", 'B', new ItemStack(Items.bucket)));
    }
}
