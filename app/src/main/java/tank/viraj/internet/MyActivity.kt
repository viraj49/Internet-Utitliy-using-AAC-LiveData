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

    private var myDataSource = MyDataSource()
    private lateinit var myViewModelFactory: MyViewModelFactory
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

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
        InternetUtil.observe(this, Observer {
            status ->
            if (status ?: false) {
                myViewModel.getData()
            }
        })
    }
}