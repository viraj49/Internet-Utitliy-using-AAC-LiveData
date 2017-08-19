package tank.viraj.internet

import io.reactivex.Maybe
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Created by Viraj Tank, 15-08-2017.
 */
class MyDataSource(val internetUtil: InternetUtil) {

    private var dataInFakeDatabase = ""
    private var numberOfNetworkCallsMade = 0

    fun getDataFromFakeDatabase(): Maybe<String> =
            if (dataInFakeDatabase.isEmpty()) {
                Maybe.empty()
            } else {
                Maybe.just(dataInFakeDatabase)
            }

    fun getDataFromFakeNetwork(): Maybe<String> {
        /* Added 3 sec delay to mimic network call */
        return Single.fromCallable { (internetUtil.isInternetOn()) }
                .flatMapMaybe { status ->
                    if (status) {
                        Maybe.just("NumberOfNetworkCallsMade = " + ++numberOfNetworkCallsMade)
                                .delay(3, TimeUnit.SECONDS)
                                .map { data ->
                                    dataInFakeDatabase = data
                                    data
                                }

                    } else {
                        throw NoInternetException()
                    }
                }
    }
}