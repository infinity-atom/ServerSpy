package com.infinityatom.serverspy;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordBot extends ListenerAdapter{

    JDA jda;
    String botUserId;

    public void init()
    {
        String token = new ServerSpy().getConfigKey("bot-token");
        JDABuilder jdabuild = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
        jda = jdabuild.build();
        jda.addEventListener(new DiscordBot());

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if(event.getChannel().getId().equals(new ServerSpy().getConfigKey("console-channel-id"))) {
            String command = event.getMessage().getContentDisplay();
            if (command.startsWith("/")) {
                command = command.substring(1);
            }
            // Run the command from the console
            String finalCommand = command;
            ServerSpy serverspyPL = (ServerSpy) Bukkit.getPluginManager().getPlugin("ServerSpy");
            Bukkit.getScheduler().runTask(serverspyPL, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand);
            });
        }

    }

    public void sendMessageToChatChannel(String messageToSend)
    {
        TextChannel textChannel = jda.getTextChannelById(new ServerSpy().getConfigKey("chat-channel-id"));
        textChannel.sendMessage(messageToSend).queue();
    }

}
