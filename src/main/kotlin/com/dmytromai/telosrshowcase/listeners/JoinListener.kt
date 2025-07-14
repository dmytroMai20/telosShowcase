package com.dmytromai.telosrshowcase.listeners

import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().name + "!"))
    }
}