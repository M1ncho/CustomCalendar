package com.example.customcalendar.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.customcalendar.calendar.CalendarUtils.Companion.getMonthList
import com.example.customcalendar.databinding.CustomCalendarBinding
import org.joda.time.DateTime


class CalendarFragment : Fragment() {

    private lateinit var binding: CustomCalendarBinding
    private var millis: Long = 0L


    companion object {
        private const val MILLIS = "MILLIS"

        fun newInstance(millis: Long) = CalendarFragment().apply {
            arguments = Bundle().apply {
                putLong(MILLIS, millis)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomCalendarBinding.inflate(layoutInflater)

        arguments?.let {
            millis = it.getLong(MILLIS)
        }
    }


    // view 설정
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CustomCalendarBinding.inflate(layoutInflater)

        var Year = DateTime(millis).toString("YYYY") + "년 "
        var Month = DateTime(millis).toString("M") + "월"
        var yearmonth = Year + Month


        binding.tvYearMonth.text = yearmonth
        binding.calenderView.initCalendar(DateTime(millis), getMonthList(DateTime(millis)))

        return binding.root
    }

}