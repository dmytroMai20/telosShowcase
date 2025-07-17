package com.dmytromai.telosrshowcase.enchantments

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class CustomEnchantmentManager(val plugin: JavaPlugin) {
    fun handleAnvilPrepare(event: PrepareAnvilEvent) {
        val left = event.inventory.getItem(0)
        val right = event.inventory.getItem(1)
        if (left == null || right == null || right.type != Material.ENCHANTED_BOOK) return

        for (enchant in CustomEnchantRegistry.enchantments) {
            val level = enchant.extractLevelFromBook(right) ?: continue
            if (!enchant.canApplyTo(left)) continue

            val result = enchant.applyTo(left, level)
            event.result = result
            return
            }
        }

    fun createCustomEnchantBook(plugin: JavaPlugin, enchant: CustomEnchant, level: Int): ItemStack {
        val book = ItemStack(Material.ENCHANTED_BOOK)
        val meta = book.itemMeta!!

        meta.setDisplayName("${ChatColor.LIGHT_PURPLE}${enchant.displayName} $level")
        meta.lore = listOf("${ChatColor.GRAY}Custom enchant: ${enchant.id}")

        val key = NamespacedKey(plugin, "custom_enchant_${enchant.id}")
        meta.persistentDataContainer.set(key, PersistentDataType.INTEGER, level)

        book.itemMeta = meta
        return book
    }

    fun start() {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            for (player in Bukkit.getOnlinePlayers()) {
                // Currently only works for armor enchantments
                val armorItems = player.inventory.armorContents.filterNotNull()
                for (item in armorItems) {
                    val meta = item.itemMeta ?: continue
                    val pdc = meta.persistentDataContainer
                    // Currently iterates over enchants which is fine if number is not huge
                    // if we wanted to optimise we can create a hashmap lookup in hashmap
                    // of enchants in directly by looking and key on item in constant time
                    for (enchant in CustomEnchantRegistry.enchantments) {
                        val key = NamespacedKey(plugin, "custom_enchant_${enchant.id}")
                        val level = pdc.get(key, PersistentDataType.INTEGER) ?: continue
                        enchant.onUpdate(player, item, level)
                    }
                }
            }
        }, 0L, 40L)
    }
}