package com.dmytromai.telosrshowcase.enchantments

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

abstract class CustomEnchant(
    val plugin: JavaPlugin,
) {
    // This implementation has all custom enchants apply an effect
    abstract val applicableItems: Array<Material>
    abstract val maxLevel: Int
    abstract val displayName: String
    abstract val tradeRecipe: ItemStack
    abstract val id: String

    fun getLore(level: Int): Component = Component.text("$displayName $level")

    open fun canApplyTo(item: ItemStack): Boolean = applicableItems.contains(item.type)

    open fun extractLevelFromBook(book: ItemStack): Int? {
        val meta = book.itemMeta ?: return null
        val key = NamespacedKey(plugin, "custom_enchant_$id")
        return meta.persistentDataContainer.get(key, PersistentDataType.INTEGER)
    }

    open fun applyTo(
        item: ItemStack,
        level: Int,
    ): ItemStack {
        val result = item.clone()
        val meta = result.itemMeta ?: return item
        val key = NamespacedKey(plugin, "custom_enchant_$id")
        val pdc = meta.persistentDataContainer

        // Add enchantment if not already present
        if (!pdc.has(key, PersistentDataType.INTEGER)) {
            pdc.set(key, PersistentDataType.INTEGER, level)
            val currentLore: List<Component> = meta.lore() ?: emptyList()
            val newLore: List<Component> = currentLore + getLore(level)
            meta.lore(newLore)
        }

        result.itemMeta = meta
        return result
    }

    abstract fun onUpdate(
        player: Player,
        item: ItemStack,
        level: Int,
    )
}
