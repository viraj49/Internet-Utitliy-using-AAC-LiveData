package tank.viraj.internet

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by Viraj Tank, 15-08-2017.
 */
class MyViewModelFactory(val myDataSource: MyDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(myDataSource) as T
        }
        throw IllegalArgumentException("Unknown MyViewModel class")
    }
}