package tank.viraj.internet

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import kotlinx.android.synthetic.main.activity.*

/**
 * Created by Viraj Tank, 15-08-2017.
 */
class MyActivity : LifecycleActivity() {

    private lateinit var internetUtil: InternetUtil
    private lateinit var myViewModelFactory: MyViewModelFactory
    private lateinit var myDataSource: MyDataSource
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        internetUtil = InternetUtil.Singleton.getInstance(application)
        myDataSource = MyDataSource(internetUtil)
        myViewModelFactory = MyViewModelFactory(myDataSource)
        myViewModel = ViewModelProviders.of(this, myViewModelFactory).get(MyViewModel::class.java)

        refresh_view.isEnabled = false

        myViewModel.myLiveData.observe(this, Observer {
            text_view.text = it?.myData
            refresh_view.isRefreshing = it?.loadingState ?: false
            if (it?.shouldWaitForInternet ?: false) waitForInternet()
        })
    }

    fun waitForInternet() {
        internetUtil.observe(this, Observer {
            status ->
            if (status ?: false) {
                myViewModel.getData()
            }
        })
    }
}