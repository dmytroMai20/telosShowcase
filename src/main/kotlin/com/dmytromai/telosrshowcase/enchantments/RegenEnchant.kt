package com.dmytromai.telosrshowcase.enchantments

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class RegenEnchant(
    plugin: JavaPlugin,
) : CustomEnchant(plugin) {
    override val applicableItems: Array<Material> =
        arrayOf(
            Material.DIAMOND_HELMET,
            Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS,
            Material.DIAMOND_BOOTS,
            Material.GOLDEN_HELMET,
            Material.GOLDEN_CHESTPLATE,
            Material.GOLDEN_LEGGINGS,
            Material.GOLDEN_BOOTS,
            Material.IRON_HELMET,
            Material.IRON_CHESTPLATE,
            Material.IRON_LEGGINGS,
            Material.IRON_BOOTS,
        )
    override val maxLevel: Int = 5
    override val displayName: String = "Regeneration"
    override val tradeRecipe: ItemStack
        get() = ItemStack(Material.EMERALD, 22)
    override val id = "regeneration"

    override fun onUpdate(
        player: Player,
        item: ItemStack,
        level: Int,
    ) {
        val duration = 20 * 3
        val amplifier = level - 1
        player.addPotionEffect(
            PotionEffect(PotionEffectType.REGENERATION, duration, amplifier, true, false, true),
        )
    }
}
