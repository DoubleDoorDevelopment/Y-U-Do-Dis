package net.doubledoordev;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.doubledoordev.YUDoDis.MOD_ID;

@Config(modid = MOD_ID, category = "All")
@Mod.EventBusSubscriber(modid = MOD_ID)
@Config.LangKey("yudodis.config.title")
public class ModConfig
{
    @Config.Name("Block Safety Zone Configs")
    @Config.Comment("Contains Safety Zones based on Dim, Y or Biome for block placement.")
    public static final BlockSafetyZone blockSafetyZone = new BlockSafetyZone();

    @Config.Name("Block Y Settings")
    @Config.Comment("Contains Y based Block placement whitelist/blacklist and fail message options.")
    public static final BlockYSettings blockYSettings = new BlockYSettings();

    @Config.Name("Block Biome Settings")
    @Config.Comment("Contains Biome based Block placement whitelist/blacklist and fail message options.")
    public static final BlockBiomeSettings blockBiomeSettings = new BlockBiomeSettings();

    @Config.Name("Block Dimension Settings")
    @Config.Comment("Contains Dimension based Block placement whitelist/blacklist and fail message options.")
    public static final BlockDimSettings blockDimSettings = new BlockDimSettings();

    @Config.Name("Item Safety Zone")
    @Config.Comment("Contains Safety Zones based on Dim, Y or Biome for Item use.")
    public static final ItemSafetyZone itemSafetyZone = new ItemSafetyZone();

    @Config.Name("Item Y Settings")
    @Config.Comment("Contains Y based Item Use whitelist/blacklist and fail message options.")
    public static final ItemYSettings itemYSettings = new ItemYSettings();

    @Config.Name("Item Biome Settings")
    @Config.Comment("Contains Biome based Item Use whitelist/blacklist and fail message options.")
    public static final ItemBiomeSettings itemBiomeSettings = new ItemBiomeSettings();

    @Config.Name("Item Dimension Settings")
    @Config.Comment("Contains Dimension based Item Use whitelist/blacklist and fail message options.")
    public static final ItemDimSettings itemDimSettings = new ItemDimSettings();

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MOD_ID))
        {
            ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
        }
    }

    public static class BlockSafetyZone
    {
        @Config.Name("Block Safe Zone Height")
        @Config.LangKey("yudodis.config.block.safezoneHeight")
        @Config.Comment({
                "Make a safe zone from Y 0 to this height, set to 0 to disable.",
                "This allows people to get out of bedrock or other bottom of world unbreakable obstacles.",
                "Can be used to limit deny zone to a small horizontal slice of the world."
        })
        public int blockSafeZoneHeight = 4;

        @Config.Name("Always Allowed Dimensions")
        @Config.LangKey("yudodis.config.block.dimalwaysallowlist")
        @Config.Comment("Dimensions block placing is always allowed. All checks are 100% ignored.")
        public int[] blockDimAlwaysAllowlist = new int[1];

        {
            blockDimAlwaysAllowlist[0] = 1;
        }

        @Config.Name("Always Allowed Biomes")
        @Config.LangKey("yudodis.config.block.biomealwaysallowdlist")
        @Config.Comment("Biomes block placing is always allowed.")
        public String[] blockBiomeAlwaysAllowlist = new String[1];

        {
            blockBiomeAlwaysAllowlist[0] = "minecraft:the_end";
        }
    }

    public static class BlockYSettings
    {
        @Config.Name("Y Whitelist")
        @Config.LangKey("yudodis.config.block.ywhitelist")
        @Config.Comment({
                "Block Item names + Y level pairs",
                "These blocks are the only placeable blocks at or below the given Y)"
        })
        public Map<String, String[]> blockYWhiteList = new HashMap<>();

        {
            String[] array = new String[1];
            array[0] = "minecraft:torch";
            blockYWhiteList.put("40", array);
        }

        @Config.Name("Y Blacklist")
        @Config.LangKey("yudodis.config.block.yblacklist")
        @Config.Comment({
                "Block Item names + Y level pairs",
                "These blocks are not placeable at or below the given Y",
        })
        public Map<String, Integer> blockYBlackList = new HashMap<>();

        {
            blockYBlackList.put("minecraft:stone", 40);
        }

        @Config.Name("Y Fail Message")
        @Config.LangKey("yudodis.config.block.yfailmessage")
        @Config.Comment("Message player gets when Y placement is blocked. Use %s to get the Y level.")
        public String blockYFailMessage = "You need to purchase the below %sY level DLC to place that here!";

        @Config.Name("Message Action Bar Toggle")
        @Config.LangKey("yudodis.config.block.yactionbartoggle")
        @Config.Comment("Toggle where the Y fail message will be placed. true = action bar, false = chat")
        public boolean blockYActionbarToggle = true;
    }

    public static class BlockBiomeSettings
    {
        @Config.Name("Biome Whitelist")
        @Config.LangKey("yudodis.config.block.biomewhitelist")
        @Config.Comment({
                "Block Item name + Biome level pairs",
                "These blocks are the only ones placeable in the given biomes"
        })
        public Map<String, String[]> blockBiomeWhiteList = new HashMap<>();

        {
            String[] array = new String[1];
            array[0] = "minecraft:taiga_hills";
            blockBiomeWhiteList.put("minecraft:torch", array);
        }

        @Config.Name("Biome Blacklist")
        @Config.LangKey("yudodis.config.block.biomeblacklist")
        @Config.Comment({
                "Block Item name + Biome level pairs",
                "These blocks are not placeable in the given biomes",
        })
        public Map<String, String[]> blockBiomeBlackList = new HashMap<>();

        {
            String[] array = new String[1];
            array[0] = "minecraft:taiga_hills";
            blockBiomeWhiteList.put("minecraft:planks", array);
        }

        @Config.Name("Always Blocked Biomes")
        @Config.LangKey("yudodis.config.block.biomealwaysblocklist")
        @Config.Comment({
                "Biomes block placing is never allowed.",
                "Safety Zone Dimensions ignore this."
        })
        public String[] blockBiomeAlwaysBlockList = new String[1];

        {
            blockBiomeAlwaysBlockList[0] = "minecraft:ocean";
        }

        @Config.Name("Fail Message")
        @Config.LangKey("yudodis.config.block.biomefailmessage")
        @Config.Comment("Message player gets when biome placement is blocked. Use %s to get the biome name.")
        public String blockBiomeFailMessage = "You need to purchase the %s biome DLC to place that here!";

        @Config.Name("Message Action Bar Toggle")
        @Config.LangKey("yudodis.config.block.biomeactionbartoggle")
        @Config.Comment("Toggle where the biome fail message will be placed. true = action bar, false = chat")
        public boolean blockBiomeActionbarToggle = true;
    }

    public static class BlockDimSettings
    {
        @Config.Name("Always Blocked Dimensions")
        @Config.LangKey("yudodis.config.block.dimalwaysblocklist")
        @Config.Comment({
                "Dimensions block placing is never allowed.",
                "Safety Zone and Always Allowed Dimensions ignore this."
        })
        public int[] blockDimAlwaysBlockList = new int[1];

        {
            blockDimAlwaysBlockList[0] = -1;
        }

        @Config.Name("Fail Message")
        @Config.LangKey("yudodis.config.block.dimfailmessage")
        @Config.Comment("Message player gets when dimension placement is blocked. Use %s to get the dimension id.")
        public String blockDimFailMessage = "You need to purchase the %s Dimension DLC to place that here!";

        @Config.Name("Message Action Bar Toggle")
        @Config.LangKey("yudodis.config.block.dimactionbartoggle")
        @Config.Comment("Toggle where the dimension fail message will be placed. true = action bar, false = chat")
        public boolean blockDimActionbarToggle = true;
    }

    public static class ItemYSettings
    {
        @Config.Name("Y Whitelist")
        @Config.LangKey("yudodis.config.item.ywhitelist")
        @Config.Comment({
                "Block Item names + Y level pairs",
                "These blocks/items are the only placeable/usable block/items at or below the given Y",
                "Using this will block item use and block placement! NOT RECOMMENDED TO USE THIS! Use Blacklist instead!"
        })
        public Map<String, String[]> itemYWhiteList = new HashMap<>();

        {
            String[] array = new String[0];
            itemYWhiteList.put("40", array);
        }

        @Config.Name("Y Blacklist")
        @Config.LangKey("yudodis.config.item.yblacklist")
        @Config.Comment({
                "Block Item names + Y level pairs",
                "These blocks/items are not placeable/usable at or below the given Y",
                "You can place blocks or items here."
        })
        public Map<String, String[]> itemYBlackList = new HashMap<>();

        {
            String[] array = new String[1];
            array[0] = "minecraft:torch";
            itemYWhiteList.put("40", array);
        }

        @Config.Name("Fail Message")
        @Config.LangKey("yudodis.config.item.yfailmessage")
        @Config.Comment("Message player gets when Y item use is blocked. Use %s to get the Y level.")
        public String itemYFailMessage = "You need to purchase the below %sY level DLC to use this item here!";

        @Config.Name("Message Action Bar Toggle")
        @Config.LangKey("yudodis.config.item.yactionbartoggle")
        @Config.Comment("Toggle where the item Y message will be placed. true = action bar, false = chat")
        public boolean itemYActionbarToggle = true;
    }

    public static class ItemBiomeSettings
    {
        @Config.Name("Biome Whitelist")
        @Config.LangKey("yudodis.config.item.biomewhitelist")
        @Config.Comment({
                "Item names + Biome pairs",
                "These items are the only ones useable in the given biomes"
        })
        public Map<String, String[]> itemBiomeWhiteList = new HashMap<>();

        {
            String[] array = new String[1];
            array[0] = "minecraft:taiga_hills";
            itemBiomeWhiteList.put("minecraft:torch", array);
        }

        @Config.Name("Biome Blacklist")
        @Config.LangKey("yudodis.config.item.biomeblacklist")
        @Config.Comment({
                "Item names + Biome pairs",
                "These items are not useable in the given biomes",
        })
        public Map<String, String[]> itemBiomeBlackList = new HashMap<>();

        {
            String[] array = new String[1];
            array[0] = "minecraft:taiga_hills";
            itemBiomeBlackList.put("minecraft:planks", array);
        }

        @Config.Name("Always Blocked Biomes")
        @Config.LangKey("yudodis.config.item.biomealwaysblocklist")
        @Config.Comment({
                "Biomes item use is never allowed.",
                "Safety Zone and Blacklisted Dimensions ignore this."
        })
        public String[] itemBiomeAlwaysBlockList = new String[1];

        {
            itemBiomeAlwaysBlockList[0] = "minecraft:ocean";
        }

        @Config.Name("Fail Message")
        @Config.LangKey("yudodis.config.item.biomefailmessage")
        @Config.Comment("Message player gets when biome item use is blocked. Use %s to get the biome name.")
        public String itemBiomeFailMessage = "You need to purchase the %s biome DLC to use this item here!";

        @Config.Name("Message Action Bar Toggle")
        @Config.LangKey("yudodis.config.item.biomeactionbartoggle")
        @Config.Comment("Toggle where the biome fail message will be placed. true = action bar, false = chat")
        public boolean itemBiomeActionbarToggle = true;
    }

    public static class ItemDimSettings
    {
        @Config.Name("Fail Message")
        @Config.LangKey("yudodis.config.item.dimfailmessage")
        @Config.Comment("Message player gets when dimension item use is blocked. Use %s to get the dimension id.")
        public String itemDimFailMessage = "You need to purchase the %s Dimension DLC to use items here!";

        @Config.Name("Message Action Bar Toggle")
        @Config.LangKey("yudodis.config.item.dimactionbartoggle")
        @Config.Comment("Toggle where the dimension fail message will be placed. true = action bar, false = chat")
        public boolean itemDimActionbarToggle = true;

        @Config.Name("Always Blocked Dimensions")
        @Config.LangKey("yudodis.config.item.dimalwaysblocklist")
        @Config.Comment({
                "Dimensions item use is never allowed.",
                "Safety Zone and Blacklisted Dimensions ignore this."
        })
        public int[] itemDimAlwaysBlockList = new int[1];

        {
            itemDimAlwaysBlockList[0] = -1;
        }
    }

    public static class ItemSafetyZone
    {
        @Config.Name("Safe Zone Height")
        @Config.LangKey("yudodis.config.item.safezoneheight")
        @Config.Comment({
                "Make a safe zone from Y 0 to this height, set to 0 to disable.",
                "This allows people to get out of bedrock or other bottom of world unbreakable obstacles.",
                "Can be used to limit deny zone to a small horizontal slice of the world."
        })
        public int itemSafeZoneHeight = 4;

        @Config.Name("Always Allowed Dimensions")
        @Config.LangKey("yudodis.config.item.dimAlwaysAllowdlist")
        @Config.Comment("Dimensions item use is always allowed. All checks are 100% ignored.")
        public int[] itemDimAlwaysAllowdlist = new int[1];

        {
            itemDimAlwaysAllowdlist[0] = 1;
        }

        @Config.Name("Always Allowd Biomes")
        @Config.LangKey("yudodis.config.item.biomealwaysallowdlist")
        @Config.Comment("Biomes item use is always allowed.")
        public String[] itemBiomeAlwaysAllowedList = new String[1];

        {
            itemBiomeAlwaysAllowedList[0] = "minecraft:the_end";
        }
    }
}
