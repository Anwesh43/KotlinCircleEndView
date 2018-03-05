package ui.anwesome.com.circleendview

/**
 * Created by anweshmishra on 06/03/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class CircleEndView(ctx : Context) : View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                
            }
        }
        return true
    }
}