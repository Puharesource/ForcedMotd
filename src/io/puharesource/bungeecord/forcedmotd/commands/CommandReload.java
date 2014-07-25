package io.puharesource.bungeecord.forcedmotd.commands;

import io.puharesource.bungeecord.forcedmotd.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandReload extends Command {
    private Main plugin;

    public CommandReload(Main plugin) {
        super("forcedmotd");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            sender.sendMessage(new ComponentBuilder("This command can only be run by the server!").color(ChatColor.RED).create());
            return;
        }
        plugin.loadConfig();
        sender.sendMessage(new ComponentBuilder("You have reloaded ForcedMotd").color(ChatColor.GREEN).create());
    }
}
