package com.example.bowlingscorecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.bowlingscorecalculator.databinding.ActivityMainBinding
import com.example.bowlingscorecalculator.logic.Game
import com.mosius.bowlingscore.models.Throw

class MainActivity : AppCompatActivity() {


    private val game = Game()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = FramesAdapter(game.frames)
       
    }

    fun buttonOnClick(view: View) {
        val value = view.tag.toString().toInt()
        game.addThrow(Throw(value))
        binding.recyclerView.adapter?.notifyDataSetChanged()
        binding.recyclerView.smoothScrollToPosition(game.frames.filterNotNull().size)
        checkButtons()
        updateTotalScore()
    }

    private fun checkButtons() {
        val possibleHits = game.getPossibleHits()
        Log.d("possibleHits", possibleHits.toString())

        for (i in 0 until binding.layoutLinear.childCount) {
            val childLayout = binding.layoutLinear.getChildAt(i) as LinearLayout
            for (j in 0 until childLayout.childCount) {
                val button = childLayout.getChildAt(j)
                button.isEnabled = i == 0 && possibleHits >= j || i != 0 && possibleHits > 5 + j
            }
        }
    }

    private fun updateTotalScore() {
        binding.textGameScore.text = getString(R.string.score, game.score.toString())
    }

    fun onClickRestart(view: View) {
        game.restart()
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}