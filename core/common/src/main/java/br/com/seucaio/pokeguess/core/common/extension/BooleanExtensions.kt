package br.com.seucaio.pokeguess.core.common.extension

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true
