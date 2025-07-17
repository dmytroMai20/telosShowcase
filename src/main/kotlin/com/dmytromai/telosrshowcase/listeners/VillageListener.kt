package com.dmytromai.telosrshowcase.listeners

import org.bukkit.NamespacedKey
import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.event.entity.VillagerAcquireTradeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import net.kyori.adventure.text.Component
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler

class VillageListener(val plugin: JavaPlugin) : Listener {
    private val regenKey = NamespacedKey(plugin, "regen_level")

    @EventHandler
    fun onVillagerAcquireTradeEvent(event: VillagerAcquireTradeEvent){
        val villager = event.entity as Villager
        if (villager.profession != Villager.Profession.ARMORER) return

        // Only occasionally offer this special trade
        if ((0..100).random() > 99) return

        val helmet = ItemStack(Material.ENCHANTED_BOOK)
        val meta = helmet.itemMeta!!
        meta.displayName(Component.text("Book of Regeneration"))
        meta.lore(listOf(Component.text("§9Regeneration I")))
        meta.persistentDataContainer.set(regenKey, PersistentDataType.INTEGER, 1)
        helmet.itemMeta = meta



        val recipe = MerchantRecipe(helmet, 5)
        recipe.addIngredient(ItemStack(Material.EMERALD, 32))
        event.recipe = recipe
    }
}