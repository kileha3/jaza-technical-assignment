package jaza.technical.assessment.utils

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import kotlin.toString

object ExceptionUtil {
    @OptIn(ExperimentalPagingApi::class)
    fun networkException(exception: Exception): Throwable {
        Log.v("KilehaDebugging", "error = ${exception.toString()} isex=${exception is retrofit2.HttpException && exception.code() == 403}")
        if(exception is  retrofit2.HttpException &&  exception.code() == 403){
            return Exception("Rate limit exceeded, try again later")
        }
        return exception
    }
}