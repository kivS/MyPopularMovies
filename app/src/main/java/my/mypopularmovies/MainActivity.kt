package my.mypopularmovies

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import my.mypopularmovies.helpers.MoviesAPI

class MainActivity : AppCompatActivity() {

    val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load data
        val moviesToFetch: String = MoviesAPI.moviesNowPlayingUrl()

        FetchMoviesTask().execute(moviesToFetch)

    }

    inner class FetchMoviesTask() : AsyncTask<String, Void, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String): String? {
            if(params.size == 0) return null

            // Get url to be fetched
            val movieUrl: String = params[0]

            try {

                val queryResponse: String = MoviesAPI.getMovies(this@MainActivity, movieUrl)

                return queryResponse


            }catch (e: Exception){
                e.printStackTrace()
                return null
            }
        }

        override fun onPostExecute(result: String?) {
            Log.d(TAG, "Result: \n $result")
        }
    }
}


