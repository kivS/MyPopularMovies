package my.mypopularmovies

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import my.mypopularmovies.helpers.MoviesAPI
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    val TAG = this.javaClass.simpleName

    val moviesListAdapter: MoviesListAdapter = MoviesListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerView Items will have the same size
        rc_movies_list.setHasFixedSize(true)

        // Set adapter
        rc_movies_list.adapter = moviesListAdapter



        // Load data
        val moviesToFetch: String = MoviesAPI.moviesNowPlayingUrl()

        FetchMoviesTask().execute(moviesToFetch)

    }

    /**
     *  Background task to get and set data to recyclerView
     */
    inner class FetchMoviesTask() : AsyncTask<String, Void, JSONArray>(){
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String): JSONArray? {
            if(params.size == 0) return null

            // Get url to be fetched
            val movieUrl: String = params[0]

            try {

                val queryResponse: String = MoviesAPI.getMovies(this@MainActivity, movieUrl)

                val parsedResponse = MoviesAPI.parseStringToJson(queryResponse)

                return parsedResponse


            }catch (e: Exception){
                e.printStackTrace()
                return null
            }
        }

        override fun onPostExecute(result: JSONArray) {

           // Set new data to adapter
            moviesListAdapter.setMovieData(result)

        }
    }
}


