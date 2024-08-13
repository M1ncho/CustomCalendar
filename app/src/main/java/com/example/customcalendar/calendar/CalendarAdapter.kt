package com.example.customcalendar.calendar

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.joda.time.DateTime

class CalendarAdapter(fm: FragmentActivity): FragmentStateAdapter(fm) {

    // 달의 첫날 구하기
    private var start : Long = DateTime().withDayOfMonth(1).withTimeAtStartOfDay().millis

    // viewpager 달력 첫 위치 설정
    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }

    // viewpager 의 item 갯수 설정 - intger의 최대값으로 해서 무한 으로 넘겨지게 보이도록 설정
    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun createFragment(position: Int): CalendarFragment {
        val millis = getItemId(position)
        return CalendarFragment.newInstance(millis)
    }


    override fun getItemId(position: Int): Long {
        return DateTime(start).plusMonths(position - START_POSITION).millis
    }

    override fun containsItem(itemId: Long): Boolean {
        val date = DateTime(itemId)

        // millisOfDay = 밀리초를 0으로 초기화 시켜서 하루 속성 가져오기
        return date.dayOfMonth == 1 && date.millisOfDay == 0
    }

}