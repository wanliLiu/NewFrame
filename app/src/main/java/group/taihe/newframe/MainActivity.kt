package group.taihe.newframe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSystem.setOnClickListener { start(ScrollingActivity::class.java) }
        btnNew.setOnClickListener { start(NewScrollingActivity::class.java) }
    }
}