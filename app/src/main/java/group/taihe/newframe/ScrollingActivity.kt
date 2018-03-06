package group.taihe.newframe

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

class ScrollingActivity : AppCompatActivity() {

    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            val ds = findViewById<View>(R.id.nestScrollView) as NestedScrollView
            val app_bar = findViewById<View>(R.id.app_bar) as AppBarLayout
            if (index == 0) {
                ds.isNestedScrollingEnabled = false
            } else if (index == 1) {
                ds.isNestedScrollingEnabled = true
            } else if (index == 2) {
                app_bar.setExpanded(true, true)
            } else if (index == 3) {
                app_bar.setExpanded(false, true)
            }
            index = (index + 1) % 4
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


    }
}
