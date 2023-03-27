package com.example.bowlingscorecalculator.logic
import android.util.Log
import com.mosius.bowlingscore.models.Frame
import com.mosius.bowlingscore.models.ScoreType

class Game {

    //Frames -> Score of Particular Frame, Score Type, List of throws that made up the frame
    val frames =  arrayOfNulls<Frame?>(10)

    // Current score of the game
    val score: Int
        get() = frames.lastOrNull { it?.score != null }?.score ?: 0

    private val throws = ArrayList<Int>()

    // Clear all game data
    fun restart() {
        throws.clear()
        frames.forEachIndexed { i, _ -> frames[i] = null } }

    fun addThrow(pinsDown: Int) {
        throws.add(pinsDown)
        calculateFrames(0, 0)
    }

    // i represent the starting index for [Throw]
    // j represent the starting index for [Frame]
    private fun calculateFrames(throwIndex: Int, frameIndex: Int) {

        // break the recursive function calling if all frames are set
        if (frameIndex == frames.size) {
            return
        }

        if (throwIndex >= throws.size) {
            calculateFrames(throwIndex, frameIndex+1)
            return
        }

        // get the previous frame score or 0 if there is not any frame yet
        // for the first frame it will be 0 because its first frame no previous frame
        val previousScore: Int? = if (frameIndex > 0) frames[frameIndex - 1]?.score else 0
        Log.d("previousScoreeee",previousScore.toString())

        when {

            // Strike
            throws[throwIndex] == 10 -> {

                strikeHit(previousScore,throws,throwIndex,frameIndex)

            }

            // Spare
            throws.size > throwIndex + 1 && (throws[throwIndex] + throws[throwIndex + 1]) == 10 -> {
                // check if Spare score can be calculated otherwise the score is null
                val score: Int? = if (throws.size > throwIndex + 2) {
                    previousScore?.let { it + 10 + throws[throwIndex + 2] }
                } else {
                    null
                }

                // create a sub list for frame throws
                val throws = ArrayList(throws.subList(
                    throwIndex,
                    if (frameIndex == 9) throws.size else Math.min(throwIndex + 2, throws.size)
                ))

                // create the frame
                frames[frameIndex] = Frame(score, ScoreType.SPARE, throws)

                // calculate next frame
                calculateFrames(throwIndex + 2, frameIndex + 1)
            }

            // Normal
            else -> {
                // Check if the score can be calculated otherwise the score is null
               /*  let is used to ensure that previousScore is not null before
                performing the score calculation. If previousScore is null, the block is skipped, and the overall
                expression returns null. If previousScore is not null, the block adds the current and next throw
                scores to the previousScore value to calculate the updated score. */
                val score: Int? = if (throws.size > throwIndex + 1) {
                    previousScore?.let { it + throws[throwIndex] + throws[throwIndex + 1] }
                } else {
                    null
                }
                Log.d("score","$score")

                val throws = ArrayList(throws.subList(
                    throwIndex,
                    Math.min(throwIndex + 2, throws.size)
                ))
                frames[frameIndex] = Frame(score, ScoreType.NORMAL, throws)

                calculateFrames(throwIndex + 2, frameIndex + 1)
            }
        }
    }

    private fun strikeHit(previousScore: Int?, throws: ArrayList<Int>, throwIndex: Int, frameIndex: Int) {
        // check if Strike score can be calculated otherwise the score is null
        val score: Int? = if (throws.size > throwIndex + 2) {
            previousScore?.let { it + 10 + throws[throwIndex + 1] + throws[throwIndex + 2] }
        }
        else {
            null
        }

        // create a sub list for frame throws
        val throws = ArrayList(throws.subList(
            throwIndex,
            if (frameIndex == 9) throws.size else Math.min(throwIndex + 1, throws.size)
        ))
        frames[frameIndex] = Frame(score, ScoreType.STRIKE, throws)
        calculateFrames(throwIndex + 1, frameIndex + 1)

    }


    fun getPossibleHits(): Int {
        if (frames.filterNotNull().size == 10) {
            val frame = frames[9]!!

            if (frame.throws.size < 3 && frame.scoreType == ScoreType.STRIKE)
                return 10

            if (frame.throws.size == 1) {
                return (10 - frame.throws[0])
            }

            if (frame.throws.size == 2 && frame.scoreType == ScoreType.SPARE) {
                return 10
            }

            return -1
        }

        val lastFrame = frames.filterNotNull().lastOrNull()

        if (
            lastFrame != null &&
            lastFrame.scoreType == ScoreType.NORMAL &&
            lastFrame.throws.size == 1
        ) {
            return (10 - lastFrame.throws[0])
        }

        return 10
    }
}