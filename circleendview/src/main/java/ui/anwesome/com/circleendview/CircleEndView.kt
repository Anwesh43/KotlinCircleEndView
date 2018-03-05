package ui.anwesome.com.circleendview

/**
 * Created by anweshmishra on 06/03/18.
 */
import android.app.Activity
import android.view.*
import android.content.*
import android.graphics.*
class CircleEndView(ctx : Context, var n : Int = 5) : View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = Renderer(this)
    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class State(var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0, var jDir : Int = 1) {
        val scales : Array<Float> = arrayOf(0f, 0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += dir * 0.1f
            if(Math.abs(scales[j] - this.prevScale) > 1) {
                scales[j] = prevScale + this.dir
                j += jDir
                if(j == scales.size || j == -1) {
                    jDir *= -1
                    j += jDir
                    prevScale = scales[j]
                    dir = 0f
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if(dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }
    data class Animator(var view : CircleEndView, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class CircleExpand(var i : Int, var n : Int) {
        val state = State()
        fun draw(canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            val r = Math.min(w,h)/20
            paint.style = Paint.Style.STROKE
            paint.color = Color.parseColor("#4CAF50")
            paint.strokeWidth = r/6
            paint.strokeCap = Paint.Cap.ROUND
            canvas.save()
            canvas.translate(r + (w -r - r) * state.scales[1], h/2)
            val y_gap = h / (2 * n +1)
            for(i in 0..n-1) {
                canvas.save()
                canvas.translate(0f, (i - n/2) * y_gap * state.scales[2])
                canvas.drawArc(RectF(-r, -r, r, r), 0f, 360f * state.scales[0], false, paint)
                canvas.restore()
            }
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : CircleEndView) {
        val circleExpand = CircleExpand(0, view.n)
        val animator = Animator(view)
        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            circleExpand.draw(canvas, paint)
            animator.animate {
                circleExpand.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            circleExpand.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity : Activity): CircleEndView {
            val view = CircleEndView(activity)
            activity.setContentView(view)
            return view
        }
    }
}