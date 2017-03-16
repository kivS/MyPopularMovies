package my.mypopularmovies

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import my.mypopularmovies.helpers.MoviesAPI
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(), MoviesListAdapter.AdapterOnMovieClickHandler {

    val TAG = this.javaClass.simpleName

    val moviesListAdapter: MoviesListAdapter = MoviesListAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerView Items will have the same size
        rc_movies_list.setHasFixedSize(true)

        // Set adapter
        rc_movies_list.adapter = moviesListAdapter

        // Load Movies
        loadMovies()


    }

    /**
     *  Background task to get and set data to recyclerView
     */
    inner class FetchMoviesTask() : AsyncTask<String, Void, JSONArray>(){
        override fun onPreExecute() {
            super.onPreExecute()

            // Handle user UI
            rc_movies_list.visibility = View.INVISIBLE
            pb_loading.visibility = View.VISIBLE

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

        override fun onPostExecute(result: JSONArray?) {

            pb_loading.visibility = View.INVISIBLE

            if(result != null && result.length() > 0){

                Log.d(TAG, "Got ${result.length()} movies")

                rc_movies_list.visibility = View.VISIBLE

                // Set new data to adapter
                moviesListAdapter.setMovieData(result)

            }else{
                // Show error
               showError(R.string.error_no_result)
            }
        }
    }


    /**
     *  Handle when movie is clicked
     */
    override fun onMovieClick(movieData: JSONObject) {
        Log.d(TAG, "Movie clicked: ${movieData.get("original_title")}")
    }


    /**
     *  Displays error to the user according to string resource supplemented
     */
    fun showError(msgId: Int){

       val errorToast = Toast.makeText(this, getString(msgId), Toast.LENGTH_LONG)
        errorToast.setGravity(Gravity.CENTER, 0,0)
        errorToast.show()
    }

    /**
     *  Check if internet connection is present
     */
    fun isOnline(): Boolean{

        val cm: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val netInfo: NetworkInfo? = cm.activeNetworkInfo

        return netInfo != null && netInfo.isConnected

    }

    /**
     *  Load movies list
     */
    fun loadMovies(){
        // check if connection is up
        if(isOnline()){
            val moviesToFetch: String = MoviesAPI.moviesNowPlayingUrl()
            FetchMoviesTask().execute(moviesToFetch)

        }else{
            showError(R.string.error_no_net)
        }


    }

}


