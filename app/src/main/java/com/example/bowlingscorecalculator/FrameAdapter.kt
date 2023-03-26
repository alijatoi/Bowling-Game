package com.example.bowlingscorecalculator

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mosius.bowlingscore.models.Frame
import com.mosius.bowlingscore.models.ScoreType

class FramesAdapter(private val frames: Array<Frame?>):RecyclerView.Adapter<FramesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_frame, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = frames[position]

        if(position==9){

            holder.text_score_3.visibility = View.VISIBLE
            holder.view_separator.visibility = View.VISIBLE
        }

        else{

            holder.text_score_3.visibility = View.GONE
            holder.view_separator.visibility = View.GONE
        }
        if (item == null) {
            holder.text_score_1.text = ""
            holder.text_score_2.text = ""
            holder.text_score_3.text = ""
            holder.text_frame_score.text = ""

        }
        if (item != null) {
            when (item.scoreType) {
                ScoreType.STRIKE -> {

                    if (position == 9) {
                        holder.text_score_3.text = "X"
                        holder.text_score_2.text = (item.throws.getOrNull(1)?.hits)?.toString() ?: ""
                    } else {
                        holder.text_score_1.text = ""
                        holder.text_score_2.text = "X"
                    }

                    holder.text_score_3.text = (item.throws.getOrNull(2)?.hits)?.toString() ?: ""

                }
                ScoreType.SPARE -> {
                    holder.text_score_1.text = (item.throws.getOrNull(0)?.hits)?.toString() ?: ""
                    holder.text_score_2.text = "/"
                    holder.text_score_3.text = (item.throws.getOrNull(2)?.hits)?.toString() ?: ""
                }
                ScoreType.NORMAL -> {
                    holder.text_score_1.text = (item.throws.getOrNull(0)?.hits)?.toString() ?: ""
                    holder.text_score_2.text = (item.throws.getOrNull(1)?.hits)?.toString() ?: ""
                    holder.text_score_3.text = (item.throws.getOrNull(2)?.hits)?.toString() ?: ""
                }
            }
        }
        if (item != null) {
            holder.text_frame_score.text = item.score?.toString() ?: ""
        }

    }

    override fun getItemCount() = 10

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text_score_1 : TextView = view.findViewById(R.id.text_score_1)
        val text_score_2 : TextView = view.findViewById(R.id.text_score_2)
        val text_score_3 : TextView = view.findViewById(R.id.text_score_3)
        val text_frame_score : TextView = view.findViewById(R.id.text_frame_score)
        val view_separator : View = view.findViewById(R.id.view_separator)
    }
}