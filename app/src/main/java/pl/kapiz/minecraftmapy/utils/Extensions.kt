package pl.kapiz.minecraftmapy.utils

fun String.trimEnd(length: Int, end: String = "..."): String {
    return if (this.length <= length) this
    else this.substring(0, length) + end
}
