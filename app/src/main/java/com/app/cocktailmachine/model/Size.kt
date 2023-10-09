package com.app.cocktailmachine.model

enum class Size(val multiplicator: Double) {
    // 1 = 100ml
    SMALL(1.5),
    MEDIUM(2.0),
    LARGE(3.0)
}