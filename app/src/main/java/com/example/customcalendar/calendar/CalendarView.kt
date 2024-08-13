package com.example.customcalendar.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.example.customcalendar.R
import com.example.customcalendar.calendar.CalendarUtils.Companion.WEEKS_PER_MONTH
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants.DAYS_PER_WEEK
import kotlin.math.max

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.calendarViewStyle,            //attribute 리소스 참조
    @StyleRes defStyleRes: Int = R.style.Calendar_CalendarViewStyle   //style 리소스 참조
) : ViewGroup(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr){

    private var calendar_height: Float = 0f


    // attrs 내부에 정의된 값들을 설정
    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            calendar_height = getDimension(R.styleable.CalendarView_dayHeight, 0f)
        }
    }


    // 높이 측정
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val h = paddingTop + paddingBottom + max(suggestedMinimumHeight, (calendar_height * WEEKS_PER_MONTH).toInt())
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec), h)
    }


    // 레이아웃 관련, measure 다음 실행
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val item_Width = (width / DAYS_PER_WEEK).toFloat()
        val item_Height = (height / DAYS_PER_WEEK).toFloat()

        var index = 0
        children.forEach { view ->
            val left = (index % DAYS_PER_WEEK) * item_Width
            val top = (index / DAYS_PER_WEEK) * item_Height

            view.layout(left.toInt(), top.toInt(), (left+item_Width).toInt(), (top+item_Height).toInt())
            index++
        }
    }


    // 달력 그리기.
    // firstDayOfMonth = 달의 시작 요일   list = 달력이 가지고 있는 요일, 이벤트 목록 (총 42개)
    fun initCalendar(firstDayOfMonth: DateTime, list: List<DateTime>) {
        list.forEach {
            addView(DayItemView(
                context = context,
                date = it,
                firstDayOfMonth = firstDayOfMonth
            ))
        }
    }


}