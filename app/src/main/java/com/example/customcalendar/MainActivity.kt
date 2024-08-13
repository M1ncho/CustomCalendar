package com.example.customcalendar

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customcalendar.calendar.CalendarAdapter
import com.example.customcalendar.databinding.ActivityMainBinding
import org.joda.time.DateTime
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var calendarAdapter : CalendarAdapter

    var cal: Calendar = Calendar.getInstance()
    private val date: DateTime = DateTime()

    var move_count = 0
    var now_position : Int? = null
    var select_month = date.year.toString()+"-"+date.monthOfYear.toString()


    companion object {
        var page_move = 0
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.hide()

        //enableEdgeToEdge()


        // calendar fragment, adapter 설정
        calendarAdapter = CalendarAdapter(this)
        binding.pagerCalendar.adapter = calendarAdapter
        binding.pagerCalendar.setCurrentItem(CalendarAdapter.START_POSITION, false)

        // 처음 시작 달의 위치 저장
        var first_position = binding.pagerCalendar.currentItem
        now_position = binding.pagerCalendar.currentItem


        // 클릭시 달 넘기기
        binding.nextCalendar.setOnClickListener {
            var current = binding.pagerCalendar.currentItem
            binding.pagerCalendar.setCurrentItem(current+1, true)
            page_move++

            Log.d("PAGE SELECTED ", "$page_move")
            Log.d("PAGE SELECTED ", select_month)
        }
        binding.prevCalendar.setOnClickListener {
            var current = binding.pagerCalendar.currentItem
            binding.pagerCalendar.setCurrentItem(current-1, true)
            page_move--

            Log.d("PAGE SELECTED ", "$page_move")
            Log.d("PAGE SELECTED ", select_month)
        }

    }


}