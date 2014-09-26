package brisk.fluiddynamics.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brisk on 25/09/14.
 */
public class FluidHelper {

    public static final Map<String,Integer> fluidColors = new HashMap<String, Integer>();

    static {
        fluidColors.put("water", MapColor.waterColor.colorValue);
        fluidColors.put("lava",  0xFF6600);
    }

    public static FluidStack getFluidStack(ItemStack itemStack) {
        if (itemStack.stackTagCompound == null || !itemStack.stackTagCompound.hasKey("Fluid")) {
            return null;
        }
        return FluidStack.loadFluidStackFromNBT(itemStack.stackTagCompound.getCompoundTag("Fluid"));
    }

    public static FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
        FluidStack fluidStack = getFluidStack(container);

        if (fluidStack == null) {
            return null;
        }
        int contents = fluidStack.amount;
        int fluidDrained = Math.min(contents, maxDrain);
        int fluidAmount = fluidStack.amount;
        fluidStack.amount = fluidDrained;

        if (doDrain) {
            if (contents == fluidDrained) {
                container.stackTagCompound.removeTag("Fluid");
                return fluidStack;
            }
            NBTTagCompound containerFluidTag = container.stackTagCompound.getCompoundTag("Fluid");
            containerFluidTag.setInteger("Amount", fluidAmount - fluidDrained);
            container.stackTagCompound.setTag("Fluid", containerFluidTag);
        }
        return fluidStack;
    }

    public static int getFluidColor(FluidStack fluidStack) {
        //the desperate search for an actual color registered ANYWHERE
        int color = fluidStack.getFluid().getColor(fluidStack);
        if (color == 0xFFFFFF) {
            //This is now an override, so no more colors should be hardcoded.
            //Ideally NO colors should be hardcoded, but forge config lacks maps :c
            Integer namedColor = fluidColors.get(fluidStack.getFluid().getName());
            if (namedColor != null) {
                return namedColor;
            }
            Block fluidBlock = fluidStack.getFluid().getBlock();
            int blockColor = fluidBlock.getBlockColor();
            if (blockColor != 0xFFFFFF) {
                return blockColor;
            }
            int mapColor = fluidBlock.getMapColor(0).colorValue;
            if (mapColor != MapColor.airColor.colorValue && (mapColor != MapColor.waterColor.colorValue)) {
                return mapColor;
            }

        }
        return color;
    }
}