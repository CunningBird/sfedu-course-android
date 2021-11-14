package com.cunningbird.schoolschedule.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cunningbird.schoolschedule.R
import java.io.File

class DailySchedule : AppCompatActivity() {

    private lateinit var listView: ListView

    private lateinit var myAdapter: Adapter

    private lateinit var newSetOfLessons: ArrayList<String>

    private var currentDay: Int = 0

    private var daysDirectory: String? = null

    private var gotSetOfLessons: ArrayList<String>? = null

    val lessons = mutableListOf<String>()

    private val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_schedule)

        initialize(intent.extras)

        buttonNextDayOnClickListener()
        editTextCountOnEditorActionListener()
    }

    private fun initialize(bundle: Bundle?) {
        currentDay = bundle!!.getInt("currentDay")
        daysDirectory = bundle.getString("daysDirectory")
        gotSetOfLessons = bundle.getStringArrayList("setOfLessons")
        newSetOfLessons = ArrayList()
    }

    private fun buttonNextDayOnClickListener() {
        val dayTextView: TextView = findViewById(R.id.day_of_week)
        dayTextView.text = days[currentDay - 1]
        val buttonNextDay: Button = findViewById(R.id.nextDay)
        buttonNextDay.isEnabled = false
        buttonNextDay.setOnClickListener {
            val file = File(daysDirectory, "day$currentDay.txt")
            file.delete()
            for (lesson in gotSetOfLessons!!) {
                newSetOfLessons.add(lesson)
            }
            for (lesson in lessons) {
                file.appendText(lesson + '\n')
                if (lesson !in gotSetOfLessons!!) {
                    newSetOfLessons.add(lesson)
                }
            }

            if (currentDay < 6) {
                val intent = Intent(this, DailySchedule::class.java)
                intent.putExtra("currentDay", currentDay + 1)
                intent.putExtra("daysDirectory", daysDirectory)
                intent.putStringArrayListExtra("setOfLessons", newSetOfLessons)
                startActivity(intent)
            } else {
                val file1 = File(daysDirectory, "subjects.txt")
                file1.delete()
                for (lesson in newSetOfLessons) {
                    file1.appendText(lesson + '\n')
                }

                val intent = Intent(this, DailySchoolSupplies::class.java)
                intent.putExtra("daysDirectory", daysDirectory)
                intent.putStringArrayListExtra("setOfLessons", newSetOfLessons)
                startActivity(intent)
            }
            finish()
        }
    }

    private fun editTextCountOnEditorActionListener() {
        val buttonNextDay: Button = findViewById(R.id.nextDay)
        var count: Int
        val editTextCount: EditText = findViewById(R.id.editTextCount)
        listView = findViewById(R.id.listView)
        listView.itemsCanFocus = true
        editTextCount.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent == null || keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                count = editTextCount.text.toString().toInt()
                if (count > 0) {
                    buttonNextDay.isEnabled = true
                }
                editTextCount.isFocusable = false

                myAdapter = Adapter(count, getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                listView.adapter = myAdapter
            }
            false
        }
    }

    private inner class Adapter(count: Int, private var layoutInflater: LayoutInflater) : BaseAdapter() {
        init {
            (1..count).forEach { i -> lessons.add("Lesson $i") }
            notifyDataSetChanged()
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return lessons.size
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
            holder.caption.setText(lessons[position])
            holder.caption.id = position
            holder.caption.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    val caption = v as EditText
                    lessons[v.id] = caption.text.toString()
                }
            }
            return view
        }
    }

    internal inner class ViewHolder {
        lateinit var caption: EditText
    }
}