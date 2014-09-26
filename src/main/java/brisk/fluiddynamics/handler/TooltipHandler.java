package brisk.fluiddynamics.handler;

import brisk.fluiddynamics.item.ItemPail;
import brisk.fluiddynamics.util.FluidHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by brisk on 25/09/14.
 */
public class TooltipHandler {

    @SubscribeEvent
    public void handleTooltipEvent(ItemTooltipEvent event) {
        //LogHelper.log("Tooltip Event thrown");
        if (event.itemStack.getItem() instanceof ItemPail) {
            //LogHelper.log("Tooltip of Pail");
            FluidStack fluid = FluidHelper.getFluidStack(event.itemStack);
            if (fluid != null) {
                if (fluid.amount > 0) {
                    event.toolTip.add(fluid.amount + "/1000 mB");
                    event.toolTip.add(fluid.getLocalizedName());
                    return;
                }
            }
            event.toolTip.add("Empty");
        }

    }
}
