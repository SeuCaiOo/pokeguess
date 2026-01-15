package br.com.seucaio.pokeguess.core.common.extension

fun Double?.defaultValue(defaultDouble: Double = 0.0) = this ?: defaultDouble

val Double.Companion.ZERO get() = 0.0

fun Double.isZero() = this == Double.ZERO

fun Double?.orZero() = this ?: Double.ZERO

fun Double?.toStringSafe(): String = defaultValue().toString()
