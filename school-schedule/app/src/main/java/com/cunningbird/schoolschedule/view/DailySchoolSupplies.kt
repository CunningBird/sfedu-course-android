package com.cunningbird.schoolschedule.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cunningbird.schoolschedule.R
import java.io.File

class DailySchoolSupplies : AppCompatActivity() {

    private lateinit var listView: ListView

    private lateinit var myAdapter: Adapter

    private val schoolSupplies = mutableListOf<ArrayList<String>>()

    private var indexLesson = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_school_supplies)

        val bundle: Bundle? = intent.extras
        val daysDirectory = bundle!!.getString("daysDirectory")
        val setOfLessons: ArrayList<String>? = bundle.getStringArrayList("setOfLessons")

        val buttonRemove: Button = findViewById(R.id.buttonRemove)
        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        val buttonPrev: Button = findViewById(R.id.buttonPrev)
        val buttonNext: Button = findViewById(R.id.buttonNext)

        val schoolSupply: TextView = findViewById(R.id.school_supply)
        schoolSupply.text = setOfLessons!![indexLesson]

        for (i in 0 until setOfLessons.size) {
            schoolSupplies.add(arrayListOf())
            schoolSupplies[i].add("")
        }

        listView = findViewById(R.id.listView)
        listView.itemsCanFocus = true
        myAdapter = Adapter(schoolSupplies[indexLesson], getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        listView.adapter = myAdapter

        buttonRemove.isEnabled = false
        buttonRemove.setOnClickListener {
            schoolSupplies[indexLesson].removeLast()
            if (schoolSupplies[indexLesson].size == 1) {
                buttonRemove.isEnabled = false
            }

            myAdapter = Adapter(schoolSupplies[indexLesson], getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            listView.adapter = myAdapter
        }

        buttonAdd.setOnClickListener {
            schoolSupplies[indexLesson].add("")
            buttonRemove.isEnabled = true

            myAdapter = Adapter(schoolSupplies[indexLesson], getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            listView.adapter = myAdapter
        }

        buttonPrev.isEnabled = false
        buttonPrev.setOnClickListener {
            indexLesson -= 1
            buttonNext.isEnabled = true
            if (indexLesson == 0) {
                buttonPrev.isEnabled = false
            }
            buttonNext.text = "B"

            buttonRemove.isEnabled = schoolSupplies[indexLesson].size > 1

            myAdapter = Adapter(schoolSupplies[indexLesson], getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            listView.adapter = myAdapter

            schoolSupply.text = setOfLessons[indexLesson]
        }

        buttonNext.setOnClickListener {
            if (indexLesson == setOfLessons.size - 1) {

                val file = File(daysDirectory, "school_supplies.txt")
                file.delete()
                for (i in 0 until schoolSupplies.size) {
                    for (supply in schoolSupplies[i]) {
                        file.appendText("$supply;")
                    }
                    file.appendText("\n")
                }

                val file2 = File(daysDirectory, "setOfLessons.txt")
                file2.delete()
                for (lesson in setOfLessons) {
                    file2.appendText(lesson + "\n")
                }



                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                indexLesson += 1
            }

            if (indexLesson == setOfLessons.size - 1) {
                buttonNext.text = "Ready"
            }

            buttonPrev.isEnabled = true

            buttonRemove.isEnabled = schoolSupplies[indexLesson].size > 1

            myAdapter = Adapter(schoolSupplies[indexLesson], getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            listView.adapter = myAdapter

            schoolSupply.text = setOfLessons[indexLesson]
        }

        initialize()
        buttonRemoveOnClickListener()
        buttonAddOnClickListener()
        buttonNextOnClickListener()
    }

    private fun initialize() {

    }

    private fun buttonRemoveOnClickListener() {

    }

    private fun buttonAddOnClickListener() {

    }

    private fun buttonNextOnClickListener() {

    }

    private inner class Adapter(val arrList: ArrayList<String>, private var layoutInflater: LayoutInflater) : BaseAdapter() {
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
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view: View? = convertView
            val holder: ViewHolder
            if (view == null) {
                holder = ViewHolder()
                view = layoutInflater.inflate(R.layout.list_item, null)
                holder.caption = view.findViewById(R.id.EditTextLesson)
                view.tag = holder
            } else {
                holder = view.tag as ViewHolder
            }
            val lessonNumber: TextView = view!!.findViewById(R.id.lessonNumber)
            lessonNumber.text = (position + 1).toString()
            holder.caption.setText(arrList[position])
            holder.caption.id = position
            holder.caption.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    val caption = v as EditText
                    arrList[v.id] = caption.text.toString()
                }
            }
            return view
        }
    }

    internal inner class ViewHolder {
        lateinit var caption: EditText
    }
}