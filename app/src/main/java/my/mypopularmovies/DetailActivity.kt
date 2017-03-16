package my.mypopularmovies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
        }
    }
}
