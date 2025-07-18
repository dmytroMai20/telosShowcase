package com.dmytromai.telosrshowcase.enchantments

import org.bukkit.Material
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.VillagerAcquireTradeEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe
import org.bukkit.plugin.java.JavaPlugin

class CustomEnchantListener(
    val plugin: JavaPlugin,
    val enchantManager: CustomEnchantmentManager,
) : Listener {
    @EventHandler
    fun onAnvilPrepare(event: PrepareAnvilEvent) {
        enchantManager.handleAnvilPrepare(event)
    }

    @EventHandler
    fun onVillagerAcquireTradeEvent(event: VillagerAcquireTradeEvent) {
        val villager = event.entity as Villager
        if (villager.profession != Villager.Profession.LIBRARIAN) return

        // Only occasionally offer this special trade
        if ((0..100).random() > 99) return

        val enchantedBook = enchantManager.createCustomEnchantBook(RegenEnchant(plugin), 1)
        val recipe = MerchantRecipe(enchantedBook, 500)
        recipe.addIngredient(ItemStack(Material.EMERALD, 22))
        event.recipe = recipe
    }
}
