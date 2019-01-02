package net.doubledoordev;

import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler
{
    //TODO: 1/100 chance for tnt sound?
    @SubscribeEvent
    public void placeEvent(BlockEvent.PlaceEvent event)
    {
        EntityPlayer player = event.getPlayer();
        IBlockState placedBlock = event.getPlacedBlock();
        String placedBlockName = placedBlock.getBlock().getRegistryName().toString();
        String biomeName = player.world.getBiome(player.getPosition()).getRegistryName().toString();
        // Check over the Dim blacklist
        for (int dim : ModConfig.dimBlacklist)
        {
            // We break out if dim is on blacklist as we don't want to continue.
            if (player.dimension == dim)
                break;

            //
            for (int alwaysDim : ModConfig.dimAlwaysBlockList)
            {
                if (player.dimension == alwaysDim)
                {
                    // Check if we are in the safezone because bedrock is bad.
                    if (ModConfig.safeZone && player.posY <= ModConfig.safeZoneHeight)
                        break;
                    // Cancel the placement and give the user feedback.
                    event.setCanceled(true);
                    player.sendStatusMessage(new TextComponentString(String.format(ModConfig.yFailMessage, alwaysDim)), ModConfig.yActionbarToggle);
                }
            }
            // If they are in a dim that is blocking check what type of list we have.
            if (!ModConfig.itemDimWhitelistOrBlacklist)
            {
                // Check over the map to block items.
                for (Map.Entry<String, Integer> entry : ModConfig.itemDimList.entrySet())
                {
                    // if the name on the list allow it.
                    if (player.posY <= entry.getValue() && !placedBlockName.equals(entry.getKey()))
                    {
                        // Check if we are in the safezone because bedrock is bad.
                        if (ModConfig.safeZone && player.posY <= ModConfig.safeZoneHeight)
                            break;
                        // Cancel the placement and give the user feedback.
                        event.setCanceled(true);
                        player.sendStatusMessage(new TextComponentString(String.format(ModConfig.yFailMessage, entry.getValue())), ModConfig.yActionbarToggle);
                    }
                }
            }
            else
            {
                // Check over the map to allow items.
                for (Map.Entry<String, Integer> entry : ModConfig.itemDimList.entrySet())
                {
                    // if the name on the list block it.
                    if (player.posY <= entry.getValue() && placedBlockName.equals(entry.getKey()))
                    {
                        // Check if we are in the safezone because bedrock is bad.
                        if (ModConfig.safeZone && player.posY <= ModConfig.safeZoneHeight)
                            break;
                        // Cancel the placement and give the user feedback.
                        event.setCanceled(true);
                        player.sendStatusMessage(new TextComponentString(String.format(ModConfig.yFailMessage, entry.getValue())), ModConfig.yActionbarToggle);
                    }
                }
            }

            // Check over the Dim blacklist
            for (String biome : ModConfig.biomeBlacklist)
            {
                // We break out if dim is on blacklist as we don't want to continue.
                if (biomeName.equals(biome))
                    break;

                //
                for (String alwaysBiome : ModConfig.biomeAlwaysBlockList)
                {
                    if (biomeName.equals(alwaysBiome))
                    {
                        // Check if we are in the safezone because bedrock is bad.
                        if (ModConfig.safeZone && player.posY <= ModConfig.safeZoneHeight)
                            break;
                        // Cancel the placement and give the user feedback.
                        event.setCanceled(true);
                        player.sendStatusMessage(new TextComponentString(String.format(ModConfig.yFailMessage, alwaysBiome)), ModConfig.yActionbarToggle);
                    }
                }
                // check over item/biome map
                for (Map.Entry<String, String> entry : ModConfig.itemBiomeList.entrySet())
                {

                    // check the whitelist/blacklist
                    if (ModConfig.itemBiomeWhitelistOrBlacklist)
                    {
                        // if the current biome the player is in matches as a whitelist (allows the item)
                        if (entry.getValue().equals(biomeName))
                        {
                            // Check if we are in the safezone because bedrock is bad.
                            if (ModConfig.safeZone && player.posY <= ModConfig.safeZoneHeight)
                                break;
                            // Cancel the placement and give the user feedback.
                            event.setCanceled(true);
                            player.sendStatusMessage(new TextComponentString(String.format(ModConfig.biomeFailMessage, entry.getValue())), ModConfig.biomeActionbarToggle);
                        }
                    }
                    else
                    {
                        // if the current biome the player is in matches as a blacklist (blocks the item)
                        if (!entry.getValue().equals(biomeName))
                        {
                            // Check if we are in the safezone because bedrock is bad.
                            if (ModConfig.safeZone && player.posY <= ModConfig.safeZoneHeight)
                                break;
                            // Cancel the placement and give the user feedback.
                            event.setCanceled(true);
                            player.sendStatusMessage(new TextComponentString(String.format(ModConfig.biomeFailMessage, entry.getValue())), ModConfig.biomeActionbarToggle);
                        }
                    }
                }
            }
        }
    }
}
