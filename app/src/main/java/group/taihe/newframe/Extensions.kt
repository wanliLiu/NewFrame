package group.taihe.newframe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue

/**
 * Created by soli on 18-3-7.
 */

fun Activity.start(cls: Class<*>) {
    startActivity(Intent(this, cls))
}

fun Context.dipToPixels(dp: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
}