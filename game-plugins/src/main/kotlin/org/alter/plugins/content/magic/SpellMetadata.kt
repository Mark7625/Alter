package org.alter.plugins.content.magic

import org.alter.game.model.item.Item

/**
 * @author Tom <rspsmods@gmail.com>
 */
data class SpellMetadata(
    val interfaceId: Int,
    val component: Int,
    val paramItem: Int,
    val spellbook: Int,
    val spellType: Int,
    val name: String,
    val lvl: Int,
    val items: List<Item>,
)
