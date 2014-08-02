package io.puharesource.bungeecord.forcedmotd.listeners;

import io.puharesource.bungeecord.forcedmotd.ForcedServer;
import io.puharesource.bungeecord.forcedmotd.Main;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ListenerPing implements Listener {
    @EventHandler
    public void onPing(ProxyPingEvent event) {
        ForcedServer forcedServer = Main.serverMap.get(Main.forcedHostsMap.get(event.getConnection().getVirtualHost().getHostName().toLowerCase()));

        if (forcedServer != null) {
            if (forcedServer.isUpdatingOnPing() || forcedServer.getInfo() == null) forcedServer.updateInfo();

            ServerPing response = event.getResponse();

            if (forcedServer.isForcingMotd()) response.setDescription(forcedServer.getMotd());
            if (forcedServer.isForcingServerIcon()) response.setFavicon(forcedServer.getFavicon());

            event.setResponse(response);
        }
    }
}
