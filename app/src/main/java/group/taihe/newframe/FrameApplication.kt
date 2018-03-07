package group.taihe.newframe

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by soli on 18-3-7.
 */
class FrameApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}