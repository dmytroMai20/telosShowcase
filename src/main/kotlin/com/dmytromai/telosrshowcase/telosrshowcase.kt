package com.dmytromai.telosrshowcase

import com.dmytromai.telosrshowcase.enchantments.CustomEnchantRegistry
import com.dmytromai.telosrshowcase.enchantments.CustomEnchantmentManager
import com.dmytromai.telosrshowcase.enchantments.RegenEnchant
import com.dmytromai.telosrshowcase.listeners.AnvilListener
import com.dmytromai.telosrshowcase.listeners.CustomEnchantListener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Bukkit
import com.dmytromai.telosrshowcase.listeners.JoinListener
import com.dmytromai.telosrshowcase.listeners.VillageListener
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.enchantments.Enchantment

class telosrshowcase : JavaPlugin() {
    private lateinit var regenKey: NamespacedKey
    override fun onEnable() {
        val enchantManager = CustomEnchantmentManager(this)
        CustomEnchantRegistry.register(RegenEnchant(this))
        Bukkit.getPluginManager().registerEvents(JoinListener(this), this)
        server.pluginManager.registerEvents(VillageListener(this), this)
        server.pluginManager.registerEvents(AnvilListener(this), this)
        server.pluginManager.registerEvents(CustomEnchantListener(this, enchantManager), this)
        enchantManager.start()
        /* Bukkit.getScheduler().runTaskTimer(this, Runnable {
            applyRegenerationToEligiblePlayers()
        }, 0L, 40L)*/

        super.onEnable()
    }

    private fun applyRegenerationToEligiblePlayers() {
        for (player in server.onlinePlayers) {
            val armor = player.inventory.armorContents
            var highestLevel = 0

            armor.forEach { item ->
                val meta = item?.itemMeta ?: return@forEach
                val level = meta.persistentDataContainer.get(regenKey, PersistentDataType.INTEGER) ?: return@forEach
                if (level > highestLevel) highestLevel = level
            }

            if (highestLevel > 0) {
                player.addPotionEffect(
                    PotionEffect(PotionEffectType.REGENERATION, 60, highestLevel - 1, true, false, true)
                )
            }
        }
    }

    override fun onDisable() {
        super.onDisable()
    }

}