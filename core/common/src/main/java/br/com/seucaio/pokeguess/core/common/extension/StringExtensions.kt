package br.com.seucaio.pokeguess.core.common.extension

val String.Companion.EMPTY get() = ""

fun String?.orDefault(default: String) = this ?: default
