package tank.viraj.internet

/**
 * Created by Viraj Tank, 15-08-2017.
 */
data class MyViewState(val myData: String = "",
                       val loadingState: Boolean = false,
                       val shouldWaitForInternet: Boolean = false)