package group.taihe.newframe

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_calendar_test.*
import java.util.*


class CalendarTestActivity : AppCompatActivity(), View.OnClickListener {

    private val permission by lazy { RxPermissions(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_test)
        checkPermission()

        createEventByIntent.setOnClickListener(this)
    }


    private fun createEvent() {
        val beginTime = Calendar.getInstance()
        beginTime.set(2018, 4, 22, 7, 30)
        val endTime = Calendar.getInstance()
        endTime.set(2018, 4, 22, 10, 30)
        val intent = Intent(Intent.ACTION_INSERT)
                .setData(Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
                .putExtra(Events.TITLE, "测试")
                .putExtra(Events.DESCRIPTION, "使用一个intent来插入事件")
                .putExtra(Events.EVENT_LOCATION, "The gym")
                .putExtra(Events.RRULE,"FREQ=DAILY;INTERVAL=2")
                .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com")
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.createEventByIntent -> createEvent()
        }
    }

    /**
     *
     */
    private fun checkPermission() {
        permission.request(Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR)
                .subscribe { pass ->
                    if (!pass) {
                        finish()
                    }
                }
    }


}
