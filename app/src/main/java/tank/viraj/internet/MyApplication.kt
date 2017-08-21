package tank.viraj.internet

import android.app.Application

/**
 * Created by Viraj Tank, 15-08-2017.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        InternetUtil.init(this)
    }
}