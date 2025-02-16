package com.app.flipcard.adapters

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.flipcard.R

class StatisticsListRecycle(
    private val statistics: List<Pair<String, Int>> // Lista de pares (nome do deck, % de acerto)
) : RecyclerView.Adapter<StatisticsListRecycle.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deckNameTextView: TextView = itemView.findViewById(R.id.deckNameTextView)
        val accuracyTextView: TextView = itemView.findViewById(R.id.accuracyTextView)
        val progressRing: ProgressBar = itemView.findViewById(R.id.progressRing)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.statistics_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (deckName, accuracy) = statistics[position]
        holder.deckNameTextView.text = deckName
        holder.accuracyTextView.text = "$accuracy%"

        // Define o progresso do anel
        holder.progressRing.progress = accuracy

        // Altera a cor do anel de progresso com base na precisão
        val ringColor = when {
            accuracy < 50 -> Color.RED
            accuracy < 80 -> Color.YELLOW
            else -> Color.GREEN
        }

        holder.progressRing.progressDrawable.setColorFilter(ringColor, PorterDuff.Mode.SRC_IN)
    }


    override fun getItemCount(): Int = statistics.size
}
