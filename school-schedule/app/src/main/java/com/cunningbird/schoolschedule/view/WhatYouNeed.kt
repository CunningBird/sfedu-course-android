package com.cunningbird.schoolschedule.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cunningbird.schoolschedule.R
import java.io.File
import java.io.FileInputStream

class WhatYouNeed : AppCompatActivity() {

    private lateinit var listView: ListView

    private lateinit var myAdapter: Adapter

    private lateinit var setOfLessons: ArrayList<String>

    private lateinit var listOfSupplies: ArrayList<ArrayList<String>>

    private lateinit var generalSupplies: ArrayList<String>

    private val days = listOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_what_you_need)

        initialize()
        setUpList()
    }

    private fun initialize() {
        val bundle: Bundle? = intent.extras
        val daysDirectory = bundle!!.getString("daysDirectory")
        val currentDay = bundle.getInt("currentDay")

        val day: TextView = findViewById(R.id.day_of_week)
        day.text = days[currentDay]

        val file1 = File(daysDirectory, "setOfLessons.txt")
        setOfLessons = ArrayList(FileInputStream(file1).bufferedReader().use { it.readText() }.lines())

        val file2 = File(daysDirectory, "school_supplies.txt")
        listOfSupplies = ArrayList()
        val stringOfSupplies = FileInputStream(file2).bufferedReader().use { it.readText() }.lines()
        for (i in 0 until setOfLessons.size) {
            val arr = ArrayList(stringOfSupplies[i].split(";"))
            arr.removeAll { j -> j.isEmpty() }
            listOfSupplies.add(arr)
        }

        val file3 = File(daysDirectory, "day" + (currentDay + 1).toString() + ".txt")
        val dayLessons = ArrayList(FileInputStream(file3).bufferedReader().use { it.readText() }.lines())

        generalSupplies = ArrayList()
        for (lesson in dayLessons) {
            generalSupplies += listOfSupplies[setOfLessons.indexOf(lesson)]
        }
    }

    private fun setUpList() {
        listView = findViewById(R.id.listView)
        listView.itemsCanFocus = true
        myAdapter = Adapter(generalSupplies, getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        listView.adapter = myAdapter
    }

    private inner class Adapter(val arrList: ArrayList<String>, private var layoutInflater: LayoutInflater) : BaseAdapter() {
        inner class ViewHolder {
            lateinit var txtName: TextView
            lateinit var checkBox: CheckBox
        }

        init {
            notifyDataSetChanged()
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return arrList.size
        }

        @SuppressLint("InflateParams")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var view: View? = convertView
            val holder: ViewHolder
            if (view == null) {
                holder = ViewHolder()
                view = layoutInflater.inflate(R.layout.check_item, null)
                holder.txtName = view.findViewById(R.id.supply)
                holder.checkBox = view.findViewById(R.id.checkBoxSupply)
                view.tag = holder
            } else {
                holder = view.tag as ViewHolder
            }
            holder.txtName.text = arrList[position]
            holder.checkBox.isChecked = false
            return view
        }
    }
}