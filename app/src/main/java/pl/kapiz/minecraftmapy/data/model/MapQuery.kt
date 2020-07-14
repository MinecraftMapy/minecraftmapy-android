/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.data.model

import android.content.Context
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.model.enum.SortMode
import pl.kapiz.minecraftmapy.utils.concat
import java.io.Serializable
import kotlin.random.Random

data class MapQuery(
    val sortBy: SortMode?,
    val sortSeed: Int? = null,
    val category: MapCategory? = null,
    val version: MinecraftVersion? = null,
    val queryText: String? = null
) : Serializable {
    companion object {
        private fun seed() = Random.nextInt(0, 99999)

        fun default() = MapQuery(
            sortBy = SortMode.DISCOVER,
            sortSeed = seed()
        )

        fun withSortBy(sortBy: SortMode) = MapQuery(
            sortBy = sortBy
        )

        fun withText(queryText: String) = MapQuery(
            sortBy = SortMode.BEST_MATCH,
            queryText = queryText
        )

        fun withCategory(category: MapCategory) = MapQuery(
            sortBy = SortMode.DISCOVER,
            sortSeed = seed(),
            category = category
        )

        fun withVersion(version: MinecraftVersion) = MapQuery(
            sortBy = SortMode.DISCOVER,
            sortSeed = seed(),
            version = version
        )
    }

    fun getTitle(context: Context): CharSequence {
        return if (queryText != null) // Szukaj: nazwa mapy
            context.getString(R.string.title_search_format, queryText)
        else if (category != null || version != null) // Wersja 1.15.2, Adventure
            listOf(
                version?.let { context.getString(R.string.title_version_format, it.name) },
                category?.name
            ).concat(", ")
        else
            context.getString(sortBy?.title ?: R.string.app_name)
    }
}
