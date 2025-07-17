package com.dmytromai.telosrshowcase.listeners

import com.dmytromai.telosrshowcase.enchantments.CustomEnchantmentManager
import com.dmytromai.telosrshowcase.enchantments.RegenEnchant
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.VillagerAcquireTradeEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class CustomEnchantListener(val plugin: JavaPlugin, val enchantManager: CustomEnchantmentManager) : Listener {
    @EventHandler
    fun onAnvilPrepare(event: PrepareAnvilEvent) {
        print(2)
        enchantManager.handleAnvilPrepare(event)
    }
    @EventHandler
    fun onVillagerAcquireTradeEvent(event: VillagerAcquireTradeEvent) {
        val villager = event.entity as Villager
        if (villager.profession != Villager.Profession.LIBRARIAN) return

        // Only occasionally offer this special trade
        if ((0..100).random() > 99) return

        val enchBook = enchantManager.createCustomEnchantBook(plugin, RegenEnchant(plugin),1)
        //val key = NamespacedKey(plugin, "custom_enchant_$")
        /*val helmet = ItemStack(Material.ENCHANTED_BOOK)
        val meta = helmet.itemMeta!!
        meta.displayName(Component.text("Book of Regeneration"))
        meta.lore(listOf(Component.text("§9Regeneration I")))
        meta.persistentDataContainer.set(regenKey, PersistentDataType.INTEGER, 1)
        helmet.itemMeta = meta*/



        val recipe = MerchantRecipe(enchBook, 500)
        recipe.addIngredient(ItemStack(Material.EMERALD, 22))
        event.recipe = recipe
    }
}