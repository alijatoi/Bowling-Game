package com.mosius.bowlingscore.models

enum class ScoreType {
    STRIKE,
    SPARE,
    NORMAL;
}

data class Throw(
    val hits: Int
)