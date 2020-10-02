package com.example.notes.retrofit

import com.example.notes.util.SharedPrefsManager
import okhttp3.*
import okio.Buffer
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(private val sharedPrefsManager: SharedPrefsManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder().url(request.url())

        val token = sharedPrefsManager.getToken()
        requestBuilder.addHeader(TOKEN, token)

        if (!bodyToString(request.body()).contains("new_session")) {
            val formBody = FormBody.Builder()
                .add(SESSION, sharedPrefsManager.getSession())
                .build()
            var postBodyString = bodyToString(request.body())
            val concat = if (postBodyString.isNotEmpty()) "&" else ""
            postBodyString = postBodyString + concat + bodyToString(formBody)
            request = requestBuilder.post(
                RequestBody.create(
                    MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
                    postBodyString
                )
            )
                .build()
            return chain.proceed(request)
        }
        return chain.proceed(requestBuilder.build())
    }

    companion object {
        const val TOKEN = "token"
        const val SESSION = "session"
    }

    private fun bodyToString(request: RequestBody?): String {
        return try {
            val buffer = Buffer()
            request?.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            ""
        }
    }
}