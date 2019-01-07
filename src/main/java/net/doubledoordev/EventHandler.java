package net.doubledoordev;

import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler
{
    @SubscribeEvent
    public void placeEvent(BlockEvent.PlaceEvent event)
    {
        EntityPlayer player = event.getPlayer();
        String placedBlockName = event.getPlacedBlock().getBlock().getRegistryName().toString();
        String biomeName = player.world.getBiome(player.getPosition()).getRegistryName().toString();
        String biomeDisplayName = player.world.getBiome(player.getPosition()).getBiomeName();
        boolean shouldCancel = false;

        // If the player is above the safe zone we need to start blocking.
        if (player.posY >= ModConfig.blockSafetyZone.blockSafeZoneHeight)
        {
            // Y/Dim based blocking start
            for (int dim : ModConfig.blockSafetyZone.blockDimAlwaysAllowlist)
            {
                // We break out if dim is on blacklist as we don't want to continue.
                if (player.dimension == dim)
                {
                    break;
                }
                // If our dim isn't on blacklist lets check for ban.
                else
                {
                    //Lets check if its an always block list before we do anything else.
                    for (int alwaysDim : ModConfig.blockDimSettings.blockDimAlwaysBlockList)
                    {
                        // if the dim matches an always block block it.
                        if (player.dimension == alwaysDim)
                        {
                            // Cancel the placement and give the user feedback.
                            event.setCanceled(true);
                            player.sendStatusMessage(new TextComponentString(String.format(ModConfig.blockDimSettings.blockDimFailMessage, alwaysDim)), ModConfig.blockDimSettings.blockDimActionbarToggle);
                            break;
                        }
                    }
                    // Check over Whitelist map.
                    for (Map.Entry<String, String[]> entry : ModConfig.blockYSettings.blockYWhiteList.entrySet())
                    {
                        // Look for any matching Y entries.
                        if (player.posY <= Integer.parseInt(entry.getKey()))
                        {
                            // If we have one check the array of item strings.
                            for (String item : entry.getValue())
                            {
                                // if our placed item doesn't match one in there block it.
                                if (!placedBlockName.equals(item))
                                {
                                    shouldCancel = false;
                                }
                                else
                                {
                                    shouldCancel = true;
                                    break;
                                }
                            }
                            if (shouldCancel)
                            {
                                // Cancel the placement and give the user feedback.
                                event.setCanceled(true);
                                player.sendStatusMessage(new TextComponentString(String.format(ModConfig.blockYSettings.blockYFailMessage, entry.getKey())), ModConfig.blockYSettings.blockYActionbarToggle);
                            }
                        }
                    }

                    // Check over Blacklist map.
                    for (Map.Entry<String, Integer> entry : ModConfig.blockYSettings.blockYBlackList.entrySet())
                    {
                        // Look for any matching blocks and Y entries.
                        if (placedBlockName.equals(entry.getKey()) && player.posY <= entry.getValue())
                        {
                            // Cancel the placement and give the user feedback.
                            event.setCanceled(true);
                            player.sendStatusMessage(new TextComponentString(String.format(ModConfig.blockYSettings.blockYFailMessage, entry.getValue())), ModConfig.blockYSettings.blockYActionbarToggle);

                        }
                    }
                }
            }

            // Biome Check Starts here.
            for (String safeBiome : ModConfig.blockSafetyZone.blockBiomeAlwaysAllowlist)
            {
                // Break out of the biomes if it is blacklisted
                if (biomeName.equals(safeBiome))
                {
                    break;
                }
                // if biome isn't on blacklist check for ban
                else
                {
                    // Check for ban alls first.
                    for (String alwaysBiome : ModConfig.blockBiomeSettings.blockBiomeAlwaysBlockList)
                    {
                        if (biomeName.equals(alwaysBiome))
                        {
                            // Cancel the placement and give the user feedback.
                            event.setCanceled(true);
                            player.sendStatusMessage(new TextComponentString(String.format(ModConfig.blockBiomeSettings.blockBiomeFailMessage, biomeDisplayName)), ModConfig.blockBiomeSettings.blockBiomeActionbarToggle);
                            break;
                        }
                    }

                    // Check over Whitelist map.
                    for (Map.Entry<String, String[]> entry : ModConfig.blockBiomeSettings.blockBiomeWhiteList.entrySet())
                    {
                        // Look for any matching Y entries.
                        if (placedBlockName.equals(entry.getKey()))
                        {
                            // If we have one check the array of item strings.
                            for (String whitelistedBiome : entry.getValue())
                            {
                                // if our placed item doesn't match one in there block it.
                                if (biomeName.equals(whitelistedBiome))
                                {
                                    shouldCancel = false;
                                }
                                else
                                {
                                    shouldCancel = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (shouldCancel)
                    {
                        // Cancel the placement and give the user feedback.
                        event.setCanceled(true);
                        player.sendStatusMessage(new TextComponentString(String.format(ModConfig.blockBiomeSettings.blockBiomeFailMessage, biomeDisplayName)), ModConfig.blockBiomeSettings.blockBiomeActionbarToggle);
                    }

                    for (Map.Entry<String, String[]> entry : ModConfig.blockBiomeSettings.blockBiomeBlackList.entrySet())
                    {
                        // Look for any matching Y entries.
                        if (placedBlockName.equals(entry.getKey()))
                        {
                            // If we have one check the array of item strings.
                            for (String blacklistedBiome : entry.getValue())
                            {
                                // if our placed item doesn't match one in there block it.
                                if (biomeName.equals(blacklistedBiome))
                                {
                                    shouldCancel = true;
                                    break;
                                }
                                else
                                {
                                    shouldCancel = false;
                                }
                            }
                        }
                    }
                    if (shouldCancel)
                    {
                        // Cancel the placement and give the user feedback.
                        event.setCanceled(true);
                        player.sendStatusMessage(new TextComponentString(String.format(ModConfig.blockBiomeSettings.blockBiomeFailMessage, biomeDisplayName)), ModConfig.blockBiomeSettings.blockBiomeActionbarToggle);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void placeEvent(PlayerInteractEvent.RightClickBlock event)
    {
        checkItems(event);
    }

    @SubscribeEvent
    public void placeEvent(PlayerInteractEvent.RightClickItem event)
    {
        checkItems(event);
    }

    public void checkItems(PlayerInteractEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        String itemUsed = event.getItemStack().getItem().getRegistryName().toString();
        String biomeName = entity.world.getBiome(entity.getPosition()).getRegistryName().toString();
        String biomeDisplayName = entity.world.getBiome(entity.getPosition()).getBiomeName();
        boolean shouldCancel = false;

        // If the player is above the safe zone we need to start blocking.
        if (entity.posY >= ModConfig.itemSafetyZone.itemSafeZoneHeight)
        {
            // Dim based blocking start
            for (int dim : ModConfig.itemSafetyZone.itemDimAlwaysAllowdlist)
            {
                // We break out if dim is on blacklist as we don't want to continue.
                if (entity.dimension == dim)
                {
                    break;
                }
                // If our dim isn't on blacklist lets check for ban.
                else
                {
                    //Lets check if its an always block list before we do anything else.
                    for (int alwaysDim : ModConfig.itemDimSettings.itemDimAlwaysBlockList)
                    {
                        // if the dim matches an always block block it.
                        if (entity.dimension == alwaysDim)
                        {
                            // Cancel the placement and give the user feedback.
                            event.setCanceled(true);
                            if (entity instanceof EntityPlayer)
                                ((EntityPlayer) entity).sendStatusMessage(new TextComponentString(String.format(ModConfig.itemDimSettings.itemDimFailMessage, alwaysDim)), ModConfig.itemDimSettings.itemDimActionbarToggle);
                        }
                    }

                    // Check over Whitelist map.
                    for (Map.Entry<String, String[]> entry : ModConfig.itemYSettings.itemYWhiteList.entrySet())
                    {
                        // Look for any matching Y entries.
                        if (entity.posY <= Integer.valueOf(entry.getKey()))
                        {
                            // If we have one check the array of item strings.
                            for (String item : entry.getValue())
                            {
                                // if our placed item doesn't match one in there block it.
                                if (itemUsed.equals(item))
                                {
                                    shouldCancel = false;
                                    break;
                                }
                                else
                                {
                                    shouldCancel = true;
                                }
                            }
                            if (shouldCancel)
                            {
                                // Cancel the placement and give the user feedback.
                                event.setCanceled(true);
                                if (entity instanceof EntityPlayer)
                                    ((EntityPlayer) entity).sendStatusMessage(new TextComponentString(String.format(ModConfig.itemYSettings.itemYFailMessage, entry.getKey())), ModConfig.itemYSettings.itemYActionbarToggle);
                            }
                        }
                    }

                    // Check over Whitelist map.
                    for (Map.Entry<String, String[]> entry : ModConfig.itemYSettings.itemYBlackList.entrySet())
                    {
                        // Look for any matching Y entries.
                        if (entity.posY <= Integer.valueOf(entry.getKey()))
                        {
                            // If we have one check the array of item strings.
                            for (String item : entry.getValue())
                            {
                                // if our placed item doesn't match one in there block it.
                                if (!itemUsed.equals(item))
                                {
                                    shouldCancel = false;
                                    break;
                                }
                                else
                                {
                                    shouldCancel = true;
                                }
                            }
                            if (shouldCancel)
                            {
                                // Cancel the placement and give the user feedback.
                                event.setCanceled(true);
                                if (entity instanceof EntityPlayer)
                                    ((EntityPlayer) entity).sendStatusMessage(new TextComponentString(String.format(ModConfig.itemYSettings.itemYFailMessage, entry.getKey())), ModConfig.itemYSettings.itemYActionbarToggle);
                            }
                        }
                    }
                }
            }
        }

        // Biome Check Starts here.
        for (String biome : ModConfig.itemSafetyZone.itemBiomeAlwaysAllowedList)
        {
            // Break out of the biomes if it is blacklisted
            if (biomeName.equals(biome))
            {
                break;
            }
            // if biome isn't on blacklist check for ban
            else
            {
                // Check for ban alls first.
                for (String alwaysBiome : ModConfig.itemBiomeSettings.itemBiomeAlwaysBlockList)
                {
                    if (biomeName.equals(alwaysBiome))
                    {
                        // Cancel the placement and give the user feedback.
                        event.setCanceled(true);
                        if (entity instanceof EntityPlayer)
                            ((EntityPlayer) entity).sendStatusMessage(new TextComponentString(String.format(ModConfig.itemBiomeSettings.itemBiomeFailMessage, biomeDisplayName)), ModConfig.itemBiomeSettings.itemBiomeActionbarToggle);
                    }
                }

                // Check over Whitelist map.
                for (Map.Entry<String, String[]> entry : ModConfig.blockBiomeSettings.blockBiomeWhiteList.entrySet())
                {
                    // Look for any matching Y entries.
                    if (biomeName.equals(entry.getKey()))
                    {
                        // If we have one check the array of item strings.
                        for (String whitelistedBiome : entry.getValue())
                        {
                            // if our placed item doesn't match one in there block it.
                            if (biomeName.equals(whitelistedBiome))
                            {
                                shouldCancel = false;
                                break;
                            }
                            else
                            {
                                shouldCancel = true;
                            }
                        }
                        if (shouldCancel)
                        {
                            // Cancel the placement and give the user feedback.
                            event.setCanceled(true);
                            if (entity instanceof EntityPlayer)
                                ((EntityPlayer) entity).sendStatusMessage(new TextComponentString(String.format(ModConfig.itemBiomeSettings.itemBiomeFailMessage, entry.getKey())), ModConfig.itemBiomeSettings.itemBiomeActionbarToggle);
                        }
                    }
                }

                for (Map.Entry<String, String[]> entry : ModConfig.blockBiomeSettings.blockBiomeBlackList.entrySet())
                {
                    // Look for any matching Y entries.
                    if (itemUsed.equals(entry.getKey()))
                    {
                        // If we have one check the array of item strings.
                        for (String blacklistedBiome : entry.getValue())
                        {
                            // if our placed item doesn't match one in there block it.
                            if (biomeName.equals(blacklistedBiome))
                            {
                                shouldCancel = true;
                                break;
                            }
                            else
                            {
                                shouldCancel = false;
                            }
                        }
                        if (shouldCancel)
                        {
                            // Cancel the placement and give the user feedback.
                            event.setCanceled(true);
                            if (entity instanceof EntityPlayer)
                                ((EntityPlayer) entity).sendStatusMessage(new TextComponentString(String.format(ModConfig.itemBiomeSettings.itemBiomeFailMessage, entry.getKey())), ModConfig.itemBiomeSettings.itemBiomeActionbarToggle);
                        }
                    }
                }
            }
        }
    }
}
