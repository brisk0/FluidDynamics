package brisk.fluiddynamics;

import brisk.fluiddynamics.client.Handler.KeyInputEventHandler;
import brisk.fluiddynamics.handler.ConfigHandler;
import brisk.fluiddynamics.handler.TooltipHandler;
import brisk.fluiddynamics.init.ModBlocks;
import brisk.fluiddynamics.init.ModItems;
import brisk.fluiddynamics.init.Recipes;
import brisk.fluiddynamics.proxy.IProxy;
import brisk.fluiddynamics.reference.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class FluidDynamics {

    @Mod.Instance(Reference.MOD_ID)
	public static FluidDynamics instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
        MinecraftForge.EVENT_BUS.register(new TooltipHandler());

        proxy.registerKeyBindings();
        ModItems.init();
        ModBlocks.init();
        Recipes.init();
    }
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());

	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
	
}