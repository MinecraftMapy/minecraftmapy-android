/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MinecraftVersion(
    val id: Int,
    val name: String,
    @SerializedName("is_snapshot")
    val isSnapshot: Boolean,
    @SerializedName("is_bedrock")
    val isBedrock: Boolean,
    @SerializedName("is_other")
    val isOther: Boolean
) : Serializable
