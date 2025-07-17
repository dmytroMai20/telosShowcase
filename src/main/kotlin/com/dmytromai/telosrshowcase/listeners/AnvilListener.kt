package com.dmytromai.telosrshowcase.listeners

import com.dmytromai.telosrshowcase.enchantments.CustomEnchantmentManager
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.plugin.java.JavaPlugin

class AnvilListener(val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onAnvilPrepare(event: PrepareAnvilEvent) {
        print(2)
        //CustomEnchantmentManager.handleAnvilPrepare(event)
    }
}