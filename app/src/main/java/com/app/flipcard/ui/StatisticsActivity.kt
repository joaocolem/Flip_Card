package com.app.flipcard.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.flipcard.adapters.StatisticsListRecycle
import com.app.flipcard.databinding.ActivityStatisticsBinding
import com.app.flipcard.repository.DeckRepository

class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var repository: DeckRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = DeckRepository(applicationContext)


        val decks = repository.getAllDecks()
        val statistics = decks.map { deck ->
            val accuracy = repository.getDeckAccuracy(deck.id)
            Pair(deck.name, accuracy)
        }


        val adapter = StatisticsListRecycle(statistics)
        binding.statisticsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.statisticsRecyclerView.adapter = adapter
    }
}
