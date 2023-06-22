package com.infinityatom.serverspy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerSpy extends JavaPlugin implements Listener {

    DiscordBot botAccess = new DiscordBot();

    public String getConfigKey(String keyname)
    {
        return this.getConfig().getString(keyname);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("ServerSpy has loaded.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("ServerSpy is stopping.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("serverspy")) {
            if(sender instanceof Player) {
                Player playerSent = (Player) sender;
                if(!playerSent.hasPermission("serverspy.info")) {
                    playerSent.sendMessage("§b§l[ServerSpy]§r §aRunning ServerSpy version 0.6 for 1.20.1");
                    playerSent.sendMessage("§eYou cannot run any sub-commands.");
                    return true;
                }
                if(args.length == 0) {
                    playerSent.sendMessage("§b§l[ServerSpy]§r §aRunning ServerSpy version 0.6 for 1.20.1");
                    playerSent.sendMessage("§eYou can use all sub-commands. (info/reload)");
                } else {
                    switch (args[0]) {
                        case "info":
                            playerSent.sendMessage("§b§l[ServerSpy]§r §aRunning ServerSpy version 0.6 for 1.20.1");
                            playerSent.sendMessage("§aBy InfinityAtom");
                            playerSent.sendMessage("§aServerSpy is licenced under the CC BY-ND 4.0 International. More information about the licence can be found in the config.");
                        case "reload":
                            playerSent.sendMessage(ChatColor.AQUA + "Reloading plugin...");
                            reloadConfig();
                    }
                }
            }
        }
        return true;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        botAccess.sendMessageToChatChannel("<" + event.getPlayer().getName() + "> " + event.getMessage());

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        botAccess.sendMessageToChatChannel(event.getPlayer().getName() + " joined. (Location X" + event.getPlayer().getLocation().getBlockX() + " Y" + event.getPlayer().getLocation().getBlockY() + " Z" + event.getPlayer().getLocation().getBlockZ() + ")");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        botAccess.sendMessageToChatChannel(event.getPlayer().getName() + " left.");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        botAccess.sendMessageToChatChannel(event.getDeathMessage());
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        botAccess.sendMessageToChatChannel(event.getPlayer().getName() + " issued server command [ " + event.getMessage() + " ]");
    }
}
