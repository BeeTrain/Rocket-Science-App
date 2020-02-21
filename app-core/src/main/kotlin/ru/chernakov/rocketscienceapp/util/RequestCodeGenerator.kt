package ru.chernakov.rocketscienceapp.util

import java.util.concurrent.atomic.AtomicInteger

object RequestCodeGenerator {
    private val SEED = AtomicInteger()

    val next: Int
        get() = SEED.incrementAndGet()
}