package nykorefa.books.http

import okhttp3.*
import org.json.JSONObject


class Http private constructor() {

    private val mBaseURL = "http://10.42.0.197:2017"
    private val mOkHttpClient = OkHttpClient()

    companion object {
        private val mInstance = Http()

        fun getInstance() = mInstance
    }

    fun post(jsonBody: JSONObject, responseCallback: Callback) {
        val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody.toString())

        val request = mGetRequestBuilder("/cypher")
            .post(requestBody)
            .build()

        mOkHttpClient.newCall(request).enqueue(responseCallback)
    }

    private fun mGetRequestBuilder(path: String): Request.Builder {
        return Request.Builder()
            .url("$mBaseURL$path")
            .header("Accept", "application/json; charset=UTF-8")
    }
}
