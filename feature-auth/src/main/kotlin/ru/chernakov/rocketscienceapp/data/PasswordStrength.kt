package ru.chernakov.rocketscienceapp.data

import ru.chernakov.rocketscienceapp.R

enum class PasswordStrength(var message: Int) {
    WEAK(R.string.password_weak),
    MEDIUM(R.string.password_medium),
    STRONG(R.string.password_strong),
    VERY_STRONG(R.string.password_very_strong);

    companion object {
        private const val MIN_LENGTH = 8
        private const val MAX_LENGTH = 15

        @Suppress("ComplexMethod", "NestedBlockDepth")
        fun calculate(password: String): PasswordStrength {
            var score = 0
            var upper = false
            var lower = false
            val digit = false
            val specialChar = false

            for (element in password) {
                if (!specialChar && !Character.isLetterOrDigit(element)) {
                    score++
                } else {
                    if (!digit && Character.isDigit(element)) {
                        score++
                    } else {
                        if (!upper || !lower) {
                            if (Character.isUpperCase(element)) {
                                upper = true
                            } else {
                                lower = true
                            }
                            if (upper && lower) {
                                score++
                            }
                        }
                    }
                }
            }
            val length = password.length
            if (length > MAX_LENGTH) {
                score++
            } else if (length < MIN_LENGTH) {
                score = 0
            }

            return when (score) {
                0 -> WEAK
                1 -> MEDIUM
                2 -> STRONG
                3 -> VERY_STRONG
                else -> VERY_STRONG
            }
        }
    }
}