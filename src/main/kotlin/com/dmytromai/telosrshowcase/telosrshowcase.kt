package com.dmytromai.telosrshowcase

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Bukkit
import com.dmytromai.telosrshowcase.listeners.JoinListener
import com.dmytromai.telosrshowcase.listeners.VillageListener

class telosrshowcase : JavaPlugin() {

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(JoinListener(), this)
        server.pluginManager.registerEvents(VillageListener(this), this)
        super.onEnable()
    }

    override fun onDisable() {
        super.onDisable()
    }

}