package net.doubledoordev;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = YUDoDis.MOD_ID,
        name = YUDoDis.MOD_NAME,
        version = YUDoDis.VERSION
)
public class YUDoDis
{

    public static final String MOD_ID = "yudodis";
    public static final String MOD_NAME = "Y_U_Do_Dis";
    public static final String VERSION = "2.0";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static YUDoDis INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}
