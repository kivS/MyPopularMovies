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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Check if intent has movie in payload
        if(intent.hasExtra("movie")){

            // Get movie object from payload
            val movie: JSONObject = JSONObject(intent.getStringExtra("movie"))

            Log.d(TAG, "Movie detail of: ${movie.get("original_title")}")

            try {

                // Get poster Image
                val posterUrl: String = MoviesAPI.getImageUrl(width = "w342", img = movie.getString("poster_path"))
                Log.d(TAG, "movie poster url: $posterUrl")

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
    }
}
