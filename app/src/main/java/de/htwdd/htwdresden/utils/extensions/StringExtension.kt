package de.htwdd.htwdresden.utils.extensions

import android.graphics.Color
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

val String?.nullWhenEmpty: String?
    get() {
        this.guard { return null }
        return if (isNullOrBlank()) {
            null
        } else {
            this!!
        }
    }

fun String?.defaultWhenNull(default: String): String {
    this.nullWhenEmpty.guard { return default }
    return this!!
}

fun String.toDate(withFormat: String = "yyyy-MM-dd"): Date? {
    return try {
        val sdf = SimpleDateFormat(withFormat, Locale.US)
        sdf.parse(this)
    } catch (e: Exception) {
        error(e)
        null
    }
}

fun String.toSHA256(): String {
    val digest = MessageDigest.getInstance("SHA-256").digest(toByteArray())
    return digest.fold("", { str, byte -> str + "%02x".format(byte) })
}

fun String.toColor() = Color.parseColor(if (startsWith("#")) { this } else { "#$this" })