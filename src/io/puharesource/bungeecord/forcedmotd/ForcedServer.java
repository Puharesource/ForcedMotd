package io.puharesource.bungeecord.forcedmotd;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;

public class ForcedServer
{
    private String name;
    private boolean isForcingMotd;
    private boolean isForcingServerIcon;
    private boolean updateOnPing;
    private ServerInfo info;
    private ServerPing pingedInfo;

    public ForcedServer(Main plugin, String name)
    {
        this.name = name;

        updateOnPing = plugin.getConfig().getBoolean("forced_servers." + name + ".update_on_ping");
        isForcingMotd = plugin.getConfig().getBoolean("forced_servers." + name + ".force_motd");
        isForcingServerIcon = plugin.getConfig().getBoolean("forced_servers." + name + ".force_icon");

        updateInfo();
    }

    public String getName()
    {
        return name;
    }

    public boolean isForcingMotd()
    {
        return isForcingMotd;
    }

    public boolean isForcingServerIcon()
    {
        return isForcingServerIcon;
    }

    public boolean isUpdatingOnPing()
    {
        return updateOnPing;
    }

    public ServerInfo getInfo()
    {
        return info;
    }

    public Favicon getFavicon()
    {
        return pingedInfo.getFaviconObject();
    }

    public String getMotd()
    {
        return pingedInfo.getDescription();
    }

    public void updateInfo()
    {
        info = BungeeCord.getInstance().getServerInfo(name);
        if(info != null) ping();
    }

    private void ping()
    {
        info.ping(new ServerPinger());
    }

    private class ServerPinger implements Callback<ServerPing>
    {
        @Override
        public void done(ServerPing serverPing, Throwable throwable)
        {
            pingedInfo = serverPing;
        }
    }
}
