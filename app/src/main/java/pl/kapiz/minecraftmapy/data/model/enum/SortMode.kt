/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.data.model.enum

enum class SortMode(val id: Int) {
    BEST_MATCH(0),          /* Trafność */
    NEWEST(1),              /* Najnowsze */
    DISCOVER(2),            /* Warto zobaczyć */
    BEST_RATED(3),          /* Najwięcej diamentów */
    MOST_DOWNLOADED(4),     /* Najwięcej pobrań */
    OLDEST(5)               /* Najstarsze */
}
