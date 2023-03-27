package com.mosius.bowlingscore.models

data class Frame(
        val score: Int?,
        val scoreType: ScoreType,
        val throws: List<Int>
)

enum class ScoreType {
        STRIKE,
        SPARE,
        NORMAL;
}

