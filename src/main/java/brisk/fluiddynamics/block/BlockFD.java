package brisk.fluiddynamics.block;

import brisk.fluiddynamics.creativetab.CreativeTabFD;
import brisk.fluiddynamics.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

/**
 * Created by brisk on 23/09/14.
 */
public class BlockFD extends Block {

    public BlockFD(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabFD.FD_TAB);
    }

    public BlockFD() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabFD.FD_TAB);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
