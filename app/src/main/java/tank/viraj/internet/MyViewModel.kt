package tank.viraj.internet

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Viraj Tank, 15-08-2017.
 */
class MyViewModel(val myDataSource: MyDataSource) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var myViewState = MyViewState()
    val myLiveData = MutableLiveData<MyViewState>()

    init {
        getData()
    }

    fun getData() {
        compositeDisposable.add(Maybe.concat(
                myDataSource.getDataFromFakeDatabase(),
                myDataSource.getDataFromFakeNetwork())
                .firstElement()
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .doOnSubscribe {
                    myViewState = myViewState.copy(loadingState = true,
                            shouldWaitForInternet = false)
                    myLiveData.postValue(myViewState)
                }
                .subscribe({ data ->
                    myViewState = myViewState.copy(myData = data,
                            loadingState = false,
                            shouldWaitForInternet = false)
                    myLiveData.postValue(myViewState)
                }) { error ->
                    if (error is NoInternetException) {
                        myViewState = myViewState.copy(myData = "No Internet",
                                loadingState = false,
                                shouldWaitForInternet = true)
                    } else {
                        myViewState = myViewState.copy(myData = "Generic Error",
                                loadingState = false,
                                shouldWaitForInternet = false)
                    }
                    myLiveData.postValue(myViewState)
                })
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}