package my.mypopularmovies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    val TAG = this.javaClass.simpleName
    private var API_KEY: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        API_KEY = getString(R.string.the_movie_db_api)

        Log.d(TAG, API_KEY)

    }
}
