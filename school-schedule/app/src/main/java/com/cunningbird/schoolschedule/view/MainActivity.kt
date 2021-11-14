package com.cunningbird.schoolschedule.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cunningbird.schoolschedule.R
import java.io.File
import java.io.FileInputStream

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    var data = ArrayList<ArrayList<String>>()

    private lateinit var daysDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        buttonCreateScheduleOnClickListener()
        setUpGrid()
    }

    private fun initialize() {
        val path = filesDir
        val dataDirectory = File(path, "data")
        dataDirectory.mkdirs()
        daysDirectory = File(dataDirectory, "days")
        daysDirectory.mkdirs()

        for (i in 1..6) {
            data.add(arrayListOf())
            val file = File(daysDirectory, "day$i.txt")
            if (file.exists()) {
                val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }.lines()
                for (line in inputAsString) {
                    if (line.isNotEmpty()) {
                        data[i - 1].add(line)
                    }
                }
                for (j in inputAsString.size..7) {
                    data[i - 1].add("")
                }
            } else {
                for (j in 1..7) {
                    data[i - 1].add("")
                }
            }
        }
    }

    private fun setUpGrid() {
        val schedule: GridView = findViewById(R.id.scheduleGrid)
        val myAdapter = Adapter(applicationContext)
        schedule.adapter = myAdapter
        schedule.onItemClickListener = this
    }

    private fun buttonCreateScheduleOnClickListener() {
        val buttonCreateSchedule: Button = findViewById(R.id.create_schedule)
        buttonCreateSchedule.setOnClickListener {
            val intent = Intent(this, DailySchedule::class.java)
            intent.putExtra("currentDay", 1)
            intent.putExtra("daysDirectory", daysDirectory.toString())
            intent.putStringArrayListExtra("setOfLessons", ArrayList<String>())
            startActivity(intent)
            finish()
        }
    }

    private inner class Adapter(var context: Context) : BaseAdapter() {
        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return data.size
        }

        @SuppressLint("InflateParams", "ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View = View.inflate(context, R.layout.table_item, null)
            val day: TextView = view.findViewById(R.id.day_of_week)

            val lesson1: TextView = view.findViewById(R.id.lesson1)
            val lesson2: TextView = view.findViewById(R.id.lesson2)
            val lesson3: TextView = view.findViewById(R.id.lesson3)
            val lesson4: TextView = view.findViewById(R.id.lesson4)
            val lesson5: TextView = view.findViewById(R.id.lesson5)
            val lesson6: TextView = view.findViewById(R.id.lesson6)
            val lesson7: TextView = view.findViewById(R.id.lesson7)

            day.text = days[position]
            lesson1.text = data[position][0]
            lesson2.text = data[position][1]
            lesson3.text = data[position][2]
            lesson4.text = data[position][3]
            lesson5.text = data[position][4]
            lesson6.text = data[position][5]
            lesson7.text = data[position][6]

            return view
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val intent = Intent(this, WhatYouNeed::class.java)
        intent.putExtra("daysDirectory", daysDirectory.toString())
        intent.putExtra("currentDay", position)
        startActivity(intent)
    }
}