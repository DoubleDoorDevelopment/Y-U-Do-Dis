package net.doubledoordev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static final String VERSION = "2.0.1";
    private static Pattern splitter = Pattern.compile("\\b([A-Za-z0-9:._\\s]+)");
    Map<String, ArrayList<String>> blockYWhiteListMap = new HashMap<>();
    Map<String, ArrayList<String>> blockYBlackListMap = new HashMap<>();
    Map<String, ArrayList<String>> blockBiomeWhiteListMap = new HashMap<>();
    Map<String, ArrayList<String>> blockBiomeBlackListMap = new HashMap<>();

    Map<String, ArrayList<String>> itemYWhiteListMap = new HashMap<>();
    Map<String, ArrayList<String>> itemYBlackListMap = new HashMap<>();
    Map<String, ArrayList<String>> itemBiomeWhiteListMap = new HashMap<>();
    Map<String, ArrayList<String>> itemBiomeBlackListMap = new HashMap<>();

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
        splitShit(ModConfig.blockYSettings.blockYWhiteList, blockYWhiteListMap);
        splitShit(ModConfig.blockYSettings.blockYBlackList, blockYBlackListMap);
        splitShit(ModConfig.blockBiomeSettings.blockBiomeWhiteList, blockBiomeWhiteListMap);
        splitShit(ModConfig.blockBiomeSettings.blockBiomeBlackList, blockBiomeBlackListMap);

        splitShit(ModConfig.itemYSettings.itemYWhiteList, itemYWhiteListMap);
        splitShit(ModConfig.itemYSettings.itemYBlackList, itemYBlackListMap);
        splitShit(ModConfig.itemBiomeSettings.itemBiomeWhiteList, itemBiomeWhiteListMap);
        splitShit(ModConfig.itemBiomeSettings.itemBiomeBlackList, itemBiomeBlackListMap);
    }

    public void splitShit(String[] configInput, Map<String, ArrayList<String>> outputSave)
    {
        ArrayList<String> array = new ArrayList<>();
        String key;
        if (configInput.length > 0)
        {
            for (String configEntry : configInput)
            {
                Matcher matcher = splitter.matcher(configEntry);
                while (matcher.find())
                {
                    array.add(matcher.group().trim());
                }
                if (!array.isEmpty())
                {
                    key = array.get(0);
                    array.remove(0);
                    outputSave.put(key, array);
                }
            }
        }
    }
}
