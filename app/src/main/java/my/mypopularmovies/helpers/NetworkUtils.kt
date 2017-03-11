package my.mypopularmovies.helpers

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by flying_potato on 11/03/2017.
 */
class NetworkUtils {
    // Initialize client
    val client: OkHttpClient = OkHttpClient()

    fun getRequest(url: String): String{

        // build get request url
        val request: Request = Request.Builder()
                .url(url)
                .build()

        // Execute request
        val response: Response = client.newCall(request).execute()

        // Check for error
        if(!response.isSuccessful) throw IOException("Unexpected: $response")

        // Get response body
        return response.body().string()
    }
}
