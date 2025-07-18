package com.dmytromai.telosrshowcase.enchantments

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class CustomEnchantmentManager(
    val plugin: JavaPlugin,
) {
    val enchantments = mutableListOf<CustomEnchant>()

    fun register(enchant: CustomEnchant) {
        enchantments.add(enchant)
    }

    fun handleAnvilPrepare(event: PrepareAnvilEvent) {
        val left = event.inventory.getItem(0)
        val right = event.inventory.getItem(1)
        if (left == null || right == null || right.type != Material.ENCHANTED_BOOK) return
        val newResult = event.result?.clone() ?: left
        for (enchant in enchantments) {
            val level = enchant.extractLevelFromBook(right) ?: continue
            if (!enchant.canApplyTo(left)) continue

            val result = enchant.applyTo(newResult, level)

            event.result = result
            return
        }
    }

    fun createCustomEnchantBook(
        enchant: CustomEnchant,
        level: Int,
    ): ItemStack {
        val book = ItemStack(Material.ENCHANTED_BOOK)
        val meta = book.itemMeta!!

        meta.displayName(Component.text("${enchant.displayName} $level").color(NamedTextColor.YELLOW))
        meta.lore(
            listOf(
                Component.text("Custom enchant: ${enchant.id}").color(NamedTextColor.GRAY),
            ),
        )

        val key = NamespacedKey(plugin, "custom_enchant_${enchant.id}")
        meta.persistentDataContainer.set(key, PersistentDataType.INTEGER, level)

        book.itemMeta = meta
        return book
    }

    fun startEffectScheduler() {
        Bukkit.getScheduler().runTaskTimer(
            plugin,
            Runnable {
                for (player in Bukkit.getOnlinePlayers()) {
                    val armorItems = player.inventory.armorContents.filterNotNull()
                    for (item in armorItems) {
                        val meta = item.itemMeta ?: continue
                        val pdc = meta.persistentDataContainer
                        for (enchant in enchantments) {
                            val key = NamespacedKey(plugin, "custom_enchant_${enchant.id}")
                            val level = pdc.get(key, PersistentDataType.INTEGER) ?: continue
                            enchant.onUpdate(player, item, level)
                        }
                    }
                }
            },
            0L,
            40L,
        )
    }
}
