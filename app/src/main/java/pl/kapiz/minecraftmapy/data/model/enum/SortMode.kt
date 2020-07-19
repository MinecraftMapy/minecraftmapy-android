/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.data.model.enum

import androidx.annotation.StringRes
import pl.kapiz.minecraftmapy.R

enum class SortMode(val id: Int, @StringRes val title: Int) {
    BEST_MATCH(0, R.string.sort_mode_best_match),               /* Trafność */
    NEWEST(1, R.string.sort_mode_newest),                       /* Najnowsze */
    DISCOVER(2, R.string.sort_mode_discover),                   /* Warto zobaczyć */
    BEST_RATED(3, R.string.sort_mode_best_rated),               /* Najwięcej diamentów */
    MOST_DOWNLOADED(4, R.string.sort_mode_most_downloaded),     /* Najwięcej pobrań */
    OLDEST(5, R.string.sort_mode_oldest)                        /* Najstarsze */
}
