package com.cunningbird.rps

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cunningbird.rps.databinding.ActivityMainBinding
import com.cunningbird.rps.usecases.Game

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var images: ArrayList<Drawable>

    private lateinit var choiceNames: ArrayList<String>

    var humanChoice: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
        playButtonClickHandler()
        radioGroupClickHandler()
    }

    private fun initialize() {
        images = arrayListOf(
            resources.getDrawable(R.drawable.rock),
            resources.getDrawable(R.drawable.scissors),
            resources.getDrawable(R.drawable.paper)
        )

        choiceNames = arrayListOf(
            "Rock",
            "Paper",
            "Scissors"
        )
    }

    private fun playButtonClickHandler() {
        binding.button.setOnClickListener {
            if (humanChoice == -1) {
                Toast.makeText(this, "Make your choice", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val game = Game(humanChoice)

            binding.answer.text = choiceNames[game.computerChoice]
            binding.tvChoose.text = choiceNames[game.computerChoice]
            binding.image.setImageDrawable(images[game.computerChoice])

            Toast.makeText(this, game.winner + " Win", Toast.LENGTH_SHORT).show()
        }
    }

    private fun radioGroupClickHandler() {
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (R.id.radio_k == i) {
                humanChoice = 0
            }
            if (R.id.radio_n == i) {
                humanChoice = 1
            }
            if (R.id.radio_b == i) {
                humanChoice = 2
            }
        }
    }
}