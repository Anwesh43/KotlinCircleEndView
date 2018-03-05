package ui.anwesome.com.kotlincircleendview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.circleendview.CircleEndView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CircleEndView.create(this)
    }
}
