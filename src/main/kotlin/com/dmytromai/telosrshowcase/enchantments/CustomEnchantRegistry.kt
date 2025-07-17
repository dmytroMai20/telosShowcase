package com.dmytromai.telosrshowcase.enchantments

object CustomEnchantRegistry {
    val enchantments = mutableListOf<CustomEnchant>()

    fun register(enchant: CustomEnchant) {
        enchantments.add(enchant)
    }

    fun getById(id: String): CustomEnchant? {
        return enchantments.find { it.id == id }
    }
}