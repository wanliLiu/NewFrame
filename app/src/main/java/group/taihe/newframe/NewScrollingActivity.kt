package group.taihe.newframe

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.WindowManager
import android.widget.TextView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_scrolling_new.*

/**
 * AppBarLayout CollapsingToolbarLayout 的进一步使用
 * http://blog.csdn.net/litengit/article/details/52958721
 */
class NewScrollingActivity : AppCompatActivity() {

    private val title = arrayOf("演出", "相册", "详情")


    private var isExpand = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling_new)
        ButterKnife.bind(this)
        //        initStatusMode();
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { v -> onBackPressed() }
        //        setTitle("");

        viewpage!!.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return TestFramgnt.newInstance(position)
            }

            override fun getCount(): Int {
                return title.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return title[position]
            }
        }
        viewpage!!.offscreenPageLimit = 3
        //        toolbTitle.setText("小酒馆音乐空间");
        //        final View group = findViewById(R.id.toolbarContent);
        //        app_bar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
        //            int scrollRangle = appBarLayout.getTotalScrollRange();
        //            //初始verticalOffset为0，不能参与计算。
        //            Log.e("位置", scrollRangle + "  ----------  " + verticalOffset);
        //            if (verticalOffset == 0) {
        ////                toolbTitle.setAlpha(0.0f);
        //                group.setBackgroundColor(getColorWithAlpha(0.0f, this.getResources().getColor(R.color.actionbar_color)));
        //            } else {
        //                //保留一位小数
        //                double alpha = Math.abs(verticalOffset) * 1.0 / scrollRangle * 1.0;
        ////                toolbTitle.setAlpha(alpha);
        //                group.setBackgroundColor(getColorWithAlpha(alpha, this.getResources().getColor(R.color.actionbar_color)));
        //            }
        //        });


        initTag()
        tablayout!!.setupWithViewPager(viewpage)
        //        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        //            @Override
        //            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //
        //            }
        //
        //            @Override
        //            public void onPageSelected(int position) {
        //                tablayout.getTabAt(position).select();
        //            }
        //
        //            @Override
        //            public void onPageScrollStateChanged(int state) {
        //
        //            }
        //        });
        //        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        //            @Override
        //            public void onTabSelected(TabLayout.Tab tab) {
        //                viewpage.setCurrentItem(tab.getPosition());
        //                TextView textView = tab.getCustomView().findViewById(R.id.text);
        //                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        //                textView.setTextColor(Color.parseColor("#FF4081"));
        //            }
        //
        //            @Override
        //            public void onTabUnselected(TabLayout.Tab tab) {
        //                TextView textView = tab.getCustomView().findViewById(R.id.text);
        //                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        //                textView.setTextColor(Color.parseColor("#96333333"));
        //            }
        //
        //            @Override
        //            public void onTabReselected(TabLayout.Tab tab) {
        //
        //            }
        //        });

        app_bar.setOnClickListener {
            isExpand = !isExpand
            app_bar.setExpanded(isExpand)
        }
    }

    private fun initTag() {
        for (i in title.indices) {
            tablayout.addTab(getTag(i, if (i == 0) true else false))
        }
    }

    /**
     * 设置状态栏是否透明
     */
    fun initStatusMode(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = window
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            return true
        } else {
            return false
        }
    }

    private fun getTag(post: Int, iselect: Boolean): TabLayout.Tab {
        val tab = tablayout.newTab()
        val view = layoutInflater.inflate(R.layout.custom_title, null)
        val textView = view.findViewById<TextView>(R.id.text)
        textView.text = title[post]
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (if (iselect) 18 else 14).toFloat())
        textView.setTextColor(Color.parseColor(if (iselect) "#FF4081" else "#96333333"))
        tab.customView = view
        return tab
    }
}
