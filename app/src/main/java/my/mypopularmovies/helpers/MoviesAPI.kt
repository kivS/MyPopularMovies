package my.mypopularmovies.helpers

import android.content.Context
import android.util.Log
import my.mypopularmovies.R
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject


/**
 * Created by flying_potato on 10/03/2017.
 */
class MoviesAPI {
    companion object{
        private val TAG = this.javaClass.simpleName
        val BASE_URL = "https://api.themoviedb.org/3"
        val IMAGE_BASE_URL =  "https://image.tmdb.org/t/p"


        fun moviesNowPlayingUrl(): String{
           return "$BASE_URL/movie/now_playing"
        }

        fun moviesPopularUrl(): String{
            return "$BASE_URL/movie/popular"
        }


        fun moviesTopRatedUrl(): String{
            return "$BASE_URL/movie/top_rated"
        }

        fun imageUrl(width: String = "w185", img: String ): String{
            return "$IMAGE_BASE_URL/$width$img"

        }


        fun parseStringToJson(stringObj: String): JSONArray{

            var parsedObj: JSONObject = JSONObject(stringObj)

            return parsedObj.getJSONArray("results")
        }


        /**
         *  GET request for movies
         */
        fun getMovies(context: Context, MoviesUrl: String): String{

            // Get API
            val  API_KEY = context.getString(R.string.the_movie_db_api)
            Log.d(TAG, "API_KEY: $API_KEY")

            // Add API KEY to url
            val url = "$MoviesUrl?api_key=$API_KEY"

            val client: OkHttpClient = OkHttpClient()

            // build get request url
            val request: Request = Request.Builder()
                    .url(url)
                    .build()

            // Execute request
            val response: Response = client.newCall(request).execute()


            // Get response body
            return response.body().string()


        }
    }

}