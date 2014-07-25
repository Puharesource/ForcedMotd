package io.puharesource.bungeecord.forcedmotd;

import io.puharesource.bungeecord.forcedmotd.commands.CommandReload;
import io.puharesource.bungeecord.forcedmotd.listeners.ListenerPing;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Main extends Plugin
{
    private Configuration config;
    private Configuration bungeeConfig;
    public static Map<String, ForcedServer> serverMap;
    public static Map<String, String> forcedHostsMap;

    @Override
    public void onEnable()
    {
        loadConfig();

        getProxy().getPluginManager().registerListener(this, new ListenerPing());
        getProxy().getPluginManager().registerCommand(this, new CommandReload(this));
    }

    public void loadConfig()
    {
        if (!getDataFolder().exists()) getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");

        try
        {
            bungeeConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("config.yml"));
            if (!file.exists()) Files.copy(getResourceAsStream("config.yml"), file.toPath());

            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        serverMap = new HashMap<>();
        forcedHostsMap = new HashMap<>();

        for(String serverName : (Set<String>)((Map)getConfig().get("forced_servers")).keySet())
        {
            getLogger().info("Loading config for server: " + serverName);
            serverMap.put(serverName, new ForcedServer(this, serverName));
        }

        LinkedHashMap<String, Object> listeners = (LinkedHashMap<String, Object>) bungeeConfig.getList("listeners").get(0);
        forcedHostsMap = (HashMap<String, String>) listeners.get("forced_hosts");

    }

    public Configuration getConfig()
    {
        return config;
    }
}
