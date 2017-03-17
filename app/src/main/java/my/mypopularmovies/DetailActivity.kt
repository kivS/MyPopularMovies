package my.mypopularmovies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import my.mypopularmovies.helpers.MoviesAPI
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName

    var movie: JSONObject = JSONObject()
    var posterUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Check if intent has movie in payload and if theres no data saved to the activity
        if(intent.hasExtra("movie") && savedInstanceState == null){

            // Get movie object from payload
            movie = JSONObject(intent.getStringExtra("movie"))
            Log.d(TAG, "Movie detail of: ${movie.get("original_title")}")

            // Get poster Image
            posterUrl = MoviesAPI.getImageUrl(width = "w342", img = movie.getString("poster_path"))
            Log.d(TAG, "movie poster url: $posterUrl")


        }else{
            if (savedInstanceState != null){
                // get data from saved state
                movie = JSONObject(savedInstanceState.getString("MOVIE_DATA"))
                posterUrl = savedInstanceState.getString("MOVIE_POSTER_URL")
            }
        }


        // Set data to views
        try {

            // Set poster image
            Picasso.with(this).load(posterUrl).into(iv_movie_poster)

            // set movie title
            tv_movie_title.text = movie.getString("original_title")

            // set vote average
            tv_movie_rating.text = "${movie.getString("vote_average")}/10"

            // set release date
            tv_movie_date.text = movie.getString("release_date")

            // set description
            tv_movie_synopsis.text = movie.getString("overview")


        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        // Save movie object to state
        outState?.putString("MOVIE_DATA", movie.toString())

        // save poster image url
        outState?.putString("MOVIE_POSTER_URL", posterUrl)
    }
}
