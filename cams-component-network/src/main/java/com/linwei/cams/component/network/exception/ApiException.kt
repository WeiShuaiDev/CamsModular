package com.linwei.cams.component.network.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import com.linwei.cams.component.network.ApiConstants
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class ApiException(var code: Int, var displayMessage: String?) : Exception() {

    companion object {
        fun handleException(throwable: Throwable): ApiException {
            val ex: ApiException
            return if (throwable is JsonParseException
                || throwable is JSONException
                || throwable is ParseException
            ) {
                //解析错误
                ex = ApiException(ApiConstants.PARSE_ERROR, throwable.message)
                ex
            } else if (throwable is ConnectException) {
                //网络错误
                ex = ApiException(ApiConstants.NETWORK_ERROR, throwable.message)
                ex
            } else if (throwable is UnknownHostException || throwable is SocketTimeoutException) {
                //连接错误
                ex = ApiException(ApiConstants.NETWORK_ERROR, throwable.message)
                ex
            } else {
                //未知错误
                ex = ApiException(ApiConstants.UNKNOWN, throwable.message)
                ex
            }
        }
    }
}
