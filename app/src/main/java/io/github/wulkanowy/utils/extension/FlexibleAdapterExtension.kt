package io.github.wulkanowy.utils.extension

import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem

fun <K : AbstractFlexibleItem<*>, T : FlexibleAdapter<K>> T.setOnItemClickListener(listener: (item: K?) -> Unit) {
    addListener(FlexibleAdapter.OnItemClickListener { _, position ->
        listener(getItem(position))
        true
    })
}

fun <T : FlexibleAdapter<*>> T.setOnUpdateListener(listener: (size: Int) -> Unit) {
    addListener(FlexibleAdapter.OnUpdateListener { listener(it) })
}
