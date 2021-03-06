package brisk.fluiddynamics.item;

import brisk.fluiddynamics.creativetab.CreativeTabFD;
import brisk.fluiddynamics.reference.Reference;
import brisk.fluiddynamics.util.FluidHelper;
import brisk.fluiddynamics.util.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;

import java.util.Map;

/**
 * Created by brisk on 22/09/14.
 */
public class ItemPail extends ItemFluidContainer {
    public IIcon pailIcon;
    public IIcon fluidIcon;

    public ItemPail()
    {
        super(0);
        setUnlocalizedName("pail");
        this.setMaxStackSize(1);
        this.setCapacity(1000);
        this.setCreativeTab(CreativeTabFD.FD_TAB);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(), "pail");
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(), "pail");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack pail, World world, EntityPlayer player) {
        MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, true);
        if (mop == null) {
            return pail;
        }
        int x = mop.blockX;
        int y = mop.blockY;
        int z = mop.blockZ;

        Block block = world.getBlock(x, y, z);
        LogHelper.log("Pail used on block " + block.getLocalizedName());

        if (block instanceof IFluidBlock) {
            scoopForge(pail, (IFluidBlock) block, world, x, y, z);
        } else if (block instanceof BlockStaticLiquid){
            scoopVanilla(pail, block, world, x, y, z);
        }

        return pail;
    }

    public boolean scoopForge(ItemStack pail, IFluidBlock fluidBlock, World world, int x, int y, int z) {
        //TODO Migrate unhelpful code to scoopDynamic. Limit this to isPailEmpty ? transfer; return true : return false
        LogHelper.log(fluidBlock.getFluid().getName());
        FluidStack fluidStack = FluidHelper.getFluidStack(pail);
        int fluidInContainer = fluidStack == null ? 0 : fluidStack.amount;
        int containerSpace = this.getCapacity(pail) - fluidInContainer;
        if (fluidBlock.canDrain(world, x, y, z)) {
            FluidStack transferStack = fluidBlock.drain(world, x, y, z, false);
            if (transferStack != null && transferStack.amount <= containerSpace) {
                if (this.fill(pail, transferStack, true) > 0) {
                    fluidBlock.drain(world, x, y, z, true);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean scoopVanilla(ItemStack pail, Block block, World world, int x, int y, int z) {
        if (this.getFluid(pail) != null) {
            return false;
        }

        Map<String, Fluid> fluids = FluidRegistry.getRegisteredFluids();
        for (Fluid fluid : fluids.values()) {
            if (block == fluid.getBlock()) {
                this.fill(pail, new FluidStack(fluid, 1000), true);
                world.setBlockToAir(x,y,z);
                return true;
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        pailIcon = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":pail");
        fluidIcon = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":pailFluid");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int pass) {
        //TODO build hashmap/array during initialisation for faster lookup.
        if (pass == 1) {
            FluidStack fluid = FluidHelper.getFluidStack(itemStack);
            if (fluid != null) {
                return FluidHelper.getFluidColor(fluid);
            }
        }
        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        if (pass == 1) {
            FluidStack fluidStack = FluidHelper.getFluidStack(stack);
            if (fluidStack != null) {
                    return fluidIcon;
            }
        }
        return pailIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    //protected int capacity = 1000;
    /*
    @Override
    public FluidStack getFluid(ItemStack container) {
        if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Fluid"))
        {
            return null;
        }
        return FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("Fluid"));
    }

    @Override
    public int getCapacity(ItemStack container) {
        return capacity;
    }

    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill)
    {
        if (resource == null)
        {
            return 0;
        }

        if (!doFill)
        {
            if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Fluid"))
            {
                return Math.min(capacity, resource.amount);
            }

            FluidStack stack = FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("Fluid"));

            if (stack == null)
            {
                return Math.min(capacity, resource.amount);
            }

            if (!stack.isFluidEqual(resource))
            {
                return 0;
            }

            return Math.min(capacity - stack.amount, resource.amount);
        }

        if (container.stackTagCompound == null)
        {
            container.stackTagCompound = new NBTTagCompound();
        }

        if (!container.stackTagCompound.hasKey("Fluid"))
        {
            NBTTagCompound fluidTag = resource.writeToNBT(new NBTTagCompound());

            if (capacity < resource.amount)
            {
                fluidTag.setInteger("Amount", capacity);
                container.stackTagCompound.setTag("Fluid", fluidTag);
                return capacity;
            }

            container.stackTagCompound.setTag("Fluid", fluidTag);
            return resource.amount;
        }

        NBTTagCompound fluidTag = container.stackTagCompound.getCompoundTag("Fluid");
        FluidStack stack = FluidStack.loadFluidStackFromNBT(fluidTag);

        if (!stack.isFluidEqual(resource))
        {
            return 0;
        }

        int filled = capacity - stack.amount;
        if (resource.amount < filled)
        {
            stack.amount += resource.amount;
            filled = resource.amount;
        }
        else
        {
            stack.amount = capacity;
        }

        container.stackTagCompound.setTag("Fluid", stack.writeToNBT(fluidTag));
        return filled;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
    {
        return FluidHelper.drain(container, maxDrain, doDrain);
    }
    */
}