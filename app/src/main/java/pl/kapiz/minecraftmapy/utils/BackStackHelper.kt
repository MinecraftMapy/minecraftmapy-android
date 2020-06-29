package pl.kapiz.minecraftmapy.utils

import android.app.Activity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackStackHelper @Inject constructor() {

    private val activities = mutableListOf<Activity>()

    fun addActivity(activity: Activity) = activities.add(activity)

    fun finishAll() {
        activities.apply {
            forEach { it.finish() }
            clear()
        }
    }
}
