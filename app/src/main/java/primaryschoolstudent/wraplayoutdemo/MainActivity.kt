package primaryschoolstudent.wraplayoutdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import primary.student.wraplayout.WrapLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            when (wrapLayout.getGravity()){
                WrapLayout.GRAVITY_CENTER -> {
                    btn.text = "重心：Left"
                    wrapLayout.setGravity(WrapLayout.GRAVITY_START)
                }
                WrapLayout.GRAVITY_START -> {
                    btn.text = "重心：right"
                    wrapLayout.setGravity(WrapLayout.GRAVITY_END)
                }
                WrapLayout.GRAVITY_END -> {
                    btn.text = "重心：center"
                    wrapLayout.setGravity(WrapLayout.GRAVITY_CENTER)
                }
            }

        }

        btn_add.setOnClickListener {
            var tv = TextView(this)
            tv.text = "新添加的TextView"
            tv.setBackgroundColor( Color.WHITE)
            var lp = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,ViewGroup.MarginLayoutParams.WRAP_CONTENT)
            lp.leftMargin = 10
            lp.rightMargin = 10
            lp.topMargin = 10
            lp.bottomMargin = 10
            wrapLayout.addView(tv,lp)
        }
    }

}
