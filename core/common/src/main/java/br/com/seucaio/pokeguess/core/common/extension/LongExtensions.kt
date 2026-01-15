package br.com.seucaio.pokeguess.core.common.extension

fun Long?.defaultValue(defaultLong: Long = 0) = this ?: defaultLong

val Long.Companion.ZERO get() = 0L

fun Long.isZero() = this == Long.ZERO

fun Long?.orZero() = this ?: Long.ZERO

fun Long?.toStringSafe(): String = defaultValue().toString()
