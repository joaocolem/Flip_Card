package com.app.flipcard

import android.app.Application
import com.app.flipcard.repository.DeckRepository

class FlipCardApplication : Application() {

    val repository: DeckRepository by lazy {
        DeckRepository(applicationContext)
    }
}