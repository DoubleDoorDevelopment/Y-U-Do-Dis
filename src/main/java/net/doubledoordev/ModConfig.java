package net.doubledoordev;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.config.Config;

@Config(modid = YUDoDis.MOD_ID)
@Config.LangKey("yudodis.config.title")
public class ModConfig
{
    @Config.LangKey("yudodis.config.yfailmessage")
    @Config.Comment("Message player gets when Y blocked.")
    public static String yFailMessage = "You need to purchase the below %sY level DLC to build here!";

    @Config.LangKey("yudodis.config.yactionbartoggle")
    @Config.Comment("Toggle where the Y message will be placed. true = action bar, false = chat")
    public static boolean yActionbarToggle = true;

    @Config.LangKey("yudodis.config.biomefailmessage")
    @Config.Comment("Message player gets when biome blocked.")
    public static String biomeFailMessage = "You need to purchase the %s biome DLC to build here!";

    @Config.LangKey("yudodis.config.biomeactionbartoggle")
    @Config.Comment("Toggle where the biome message will be placed. true = action bar, false = chat")
    public static boolean biomeActionbarToggle = true;

    @Config.LangKey("yudodis.config.itemdimlist")
    @Config.Comment("Block, Dim pairs for the blocking or allowing")
    public static Map<String, Integer> itemDimList = new HashMap<>();

    static
    {
        itemDimList.put("minecraft:torch", 40);
        itemDimList.put("minecraft:water", 40);
    }

    @Config.LangKey("yudodis.config.itembiomelist")
    @Config.Comment("Block, biome pairs for the blocking or allowing")
    public static Map<String, String> itemBiomeList = new HashMap<>();

    static
    {
        itemBiomeList.put("minecraft:water", "minecraft:ocean");
    }

    @Config.LangKey("yudodis.config.itemdimwhitelistorblacklist")
    @Config.Comment("Changes how itemList is treated, True = Whitelist (only these can be placed), False = Blacklist (only these can't be placed).")
    public static boolean itemDimWhitelistOrBlacklist = false;

    @Config.LangKey("yudodis.config.itembiomewhitelistorblacklist")
    @Config.Comment("Changes how itemBiome is treated, True = Whitelist (only these can be placed), False = Blacklist (only these can't be placed).")
    public static boolean itemBiomeWhitelistOrBlacklist = true;

    @Config.LangKey("yudodis.config.safezone")
    @Config.Comment("Allows an area to still be placed in, height depends on safeZoneHeight")
    public static boolean safeZone = false;

    @Config.LangKey("yudodis.config.safezoneHeight")
    @Config.Comment("Y level everything is ignored, Allows for a safezone so people can't get stuck in bedrock if safeZone is enabled.")
    public static int safeZoneHeight = 4;

    @Config.LangKey("yudodis.config.dimBlacklist")
    @Config.Comment("Dims the mod will not work in, Ever, even if you add items anywhere.")
    public static int[] dimBlacklist = new int[1];

    static
    {
        dimBlacklist[0] = 1;
    }

    @Config.LangKey("yudodis.config.biomeblacklist")
    @Config.Comment("Dims the mod will not work in, Ever, even if you add items anywhere.")
    public static String[] biomeBlacklist = new String[1];

    static
    {
        biomeBlacklist[0] = "minecraft:the_end";
    }

    @Config.LangKey("yudodis.config.dimalwaysblocklist")
    @Config.Comment({"Dims the mod will ALWAYS block placement in.",
            "ALWAYS regardless of other settings(excluding safezone and blacklist)."})
    public static int[] dimAlwaysBlockList = new int[1];

    static
    {
        dimAlwaysBlockList[0] = -1;
    }

    @Config.LangKey("yudodis.config.biomealwaysblocklist")
    @Config.Comment({"Biomes the mod will ALWAYS block placement in.",
            "ALWAYS regardless of other settings(excluding safezone and blacklist)."})
    public static String[] biomeAlwaysBlockList = new String[1];

    static
    {
        biomeAlwaysBlockList[0] = "minecraft:ocean";
    }
}
