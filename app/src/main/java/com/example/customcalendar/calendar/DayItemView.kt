package com.example.customcalendar.calendar

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.MotionEvent
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import com.example.customcalendar.R
import com.example.customcalendar.calendar.CalendarUtils.Companion.isSameMonth
import org.joda.time.DateTime
import org.joda.time.LocalDate

class DayItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes private val defStyleAttr: Int = R.attr.itemViewStyle,
    @StyleRes private val defStyleRes: Int = R.style.Calendar_ItemViewStyle,
    private val date: DateTime = DateTime(),
    private val firstDayOfMonth: DateTime = DateTime()
): View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {


    private val bounds = Rect()

    private var paint_text: Paint = Paint()
    private var paint_circle: Paint = Paint()   // 오늘 날짜 표시 용도 원을 그릴 paint


    //현재 시간 구하기
    private var now = LocalDate.now()
    private val nowDay = now.dayOfMonth.toString()
    private val nowYearMonth = now.year.toString() + now.monthOfYear.toString()

    // 비교할 시간. 슬라이드 하면서 바뀔 년달
    private val YearMonth = firstDayOfMonth.year.toString() + firstDayOfMonth.monthOfYear.toString()

    // 그리는 날짜 포맷, 데이터 통신 떄 사용할 날짜, 현재 데이터 모드
    val savedate = DateTime(date).toString("YYYY-MM-dd")

    //activity 구하기
    var nowActivity = getActivity()

    // long 클릭 구현시 사용
    var isLongClick = false
    private var LONG_PRESS_TIME = 1000
    lateinit var thread: Thread



    /* Attributes 초기화 */
    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            val dayTextSize = getDimensionPixelSize(R.styleable.CalendarView_dayTextSize, 0).toFloat()

            // 날짜 그리기 설정
            paint_text = TextPaint().apply {
                isAntiAlias = true
                textSize = dayTextSize
                color = Color.BLACK

                // 속한 달이 아니면 40 보이기로 보여주기
                if(!isSameMonth(date, firstDayOfMonth)) {
                    alpha = 20
                }
            }

            // 현재 당일 날짜에 표시할 원 그리기 설정
            paint_circle.apply {
                isAntiAlias = true
                isDither = true
                strokeWidth = 4.5f
                style = Paint.Style.STROKE
                color = Color.rgb(250, 10, 120)
            }

        }
    }



    // 화면에 그릴 내용
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (canvas == null) return

        val mdate = date
        val date = date.dayOfMonth.toString()

        paint_text.getTextBounds(date, 0, date.length, bounds)
        canvas.drawText(
            date,
            (width / 2 - bounds.width() / 2).toFloat(),
            (height / 2 + bounds.height() / 2).toFloat(),
            paint_text
        )

        // 현재 날짜 표시하는 원
        if(isSameMonth(mdate, firstDayOfMonth)) {
            if (nowDay.equals(date) && nowYearMonth.equals(YearMonth)) {
                canvas.drawCircle(
                    (width / 2 + 3).toFloat(),
                    (height / 2 + bounds.height() / 2 - 16.5).toFloat(),
                    41.5f,
                    paint_circle
                )
            }
        }
    }



    // 날짜 선택시 실행할 함수
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        when (event?.action){
//            MotionEvent.ACTION_DOWN -> {
//                if (nowActivity == diaryActivity) {
//                    thread = getLongClickThread()
//                    thread.start()
//                }
//            }
//            MotionEvent.ACTION_UP -> {
//                if (nowActivity == diaryActivity) {
//                    DiaryTouch()
//                    thread.interrupt()
//                    isLongClick = false
//                }
//            }
//            MotionEvent.ACTION_MOVE -> {
//                if (isLongClick) {
//                }
//            }
//        }
//        return true
//    }


    // long press 시 실행
//    fun getLongClickThread() : Thread {
//        return Thread(Runnable {
//            try {
//                Thread.sleep(LONG_PRESS_TIME.toLong())
//
//                // 예외 발생이 아니면. 즉 지정 시간동안 누를 시
//                if (!Thread.currentThread().isInterrupted) {
//                    isLongClick = true
//                    Log.d("THREAD LONG CLICK ", "Start  $date")
//
//                    (getActivity() as DiaryActivity).Long_press_popup(savedate)
//                }
//            }
//            catch (e: InterruptedException) {}
//            finally { }
//        })
//    }
//
//
//
//    private fun DiaryTouch() {
//        if (nowActivity == diaryActivity) {
//            // 선택한 날짜로 diary 설정
//            if(isSameMonth(date, firstDayOfMonth)) {
//                (getActivity() as DiaryActivity).setWantDay(date)
//            }
//        }
//        else {
//            Log.d("ERROR", "not diary activity")
//        }
//    }




    // 해당 fragment에서 사용하는  activity 가져오기
    private fun getActivity(): Activity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }


}