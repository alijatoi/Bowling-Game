package com.mosius.bowlingscore.models

data class Frame(
        val score: Int?,
        val scoreType: ScoreType,
        val throws: List<Throw>
)
data class Throw(
        val hits: Int
)

enum class ScoreType {
        STRIKE,
        SPARE,
        NORMAL;
}

