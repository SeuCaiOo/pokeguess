package br.com.seucaio.pokeguess.core.common.extension

fun Int?.defaultValue(defaultInt: Int = 0) = this ?: defaultInt

val Int.Companion.ZERO get() = 0

fun Int.isZero() = this == Int.ZERO

fun Int?.orZero() = this ?: Int.ZERO

fun Int?.toStringSafe() : String = defaultValue().toString()