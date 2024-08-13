package com.example.customcalendar.calendar

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

class CalendarUtils {

    companion object {

        const val WEEKS_PER_MONTH = 6


        // 월간 달을 반환
        fun getMonthList(datetime: DateTime): List<DateTime> {
            val list = mutableListOf<DateTime>()    // 읽기 및 쓰기가 가능한 list , 동적으로 할당되는 배열을 활용할 때 사용

            val date = datetime.withDayOfMonth(1)
            val prev = getPrevOffset(date)

            val startValue = date.minusDays(prev)     // 시작값을 이전달의 작은 값으로 설정

            val totalDay = DateTimeConstants.DAYS_PER_WEEK * WEEKS_PER_MONTH   //달력에 그릴 총 날짜의 갯수

            // list에 startValue 에서 totalDay 까지 하루를 더하면서 추가
            for (i in 0 until totalDay) {
                list.add(DateTime(startValue.plusDays(i)))
            }

            return list
        }


        // 해당 월 캘린더의 이전달 날짜 갯수를 반환
        private fun getPrevOffset(datetime: DateTime): Int {
            var prevMonthTailOffset = datetime.dayOfWeek

            if (prevMonthTailOffset >=7 ) {
                prevMonthTailOffset %= 7
            }

            return prevMonthTailOffset
        }


        // 2개의 달을 같은 달인지 비교하여 반환
        fun isSameMonth(first: DateTime, second: DateTime): Boolean =
            first.year == second.year && first.monthOfYear == second.monthOfYear


    }


}