package com.dmytromai.telosrshowcase

import com.dmytromai.telosrshowcase.enchantments.CustomEnchantListener
import com.dmytromai.telosrshowcase.enchantments.CustomEnchantmentManager
import com.dmytromai.telosrshowcase.enchantments.RegenEnchant
import com.dmytromai.telosrshowcase.listeners.JoinListener
import org.bukkit.plugin.java.JavaPlugin

class TelosrShowcase : JavaPlugin() {
    // Companion object so that plugin and CustomEnchantmentManager is globally accessible for extension later
    companion object {
        lateinit var instance: TelosrShowcase
            private set

        lateinit var enchantManager: CustomEnchantmentManager
            private set
    }

    override fun onEnable() {
        instance = this
        enchantManager = CustomEnchantmentManager(this)
        enchantManager.register(RegenEnchant(this))
        server.pluginManager.registerEvents(
            JoinListener(this),
            this,
        )
        server.pluginManager.registerEvents(
            CustomEnchantListener(
                this,
                enchantManager,
            ),
            this,
        )
        enchantManager.startEffectScheduler()
    }

    override fun onDisable() {
    }
}
