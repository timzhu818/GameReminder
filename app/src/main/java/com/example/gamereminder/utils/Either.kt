package com.example.gamereminder.utils

sealed class Either<out L, out R>

data class Failure<out L>(val l: L) : Either<L, Nothing>()

data class Success<out R>(val r: R) : Either<Nothing, R>()

