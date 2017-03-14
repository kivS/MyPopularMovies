package my.mypopularmovies

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_item.view.*
import my.mypopularmovies.helpers.MoviesAPI
import org.json.JSONArray
import org.json.JSONObject

/**
 * RecyclerView Adapter for movies list
 */
class MoviesListAdapter() : RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {

    val TAG = javaClass.simpleName

    var moviesList: JSONArray = JSONArray()

    /**
     *  View holder for movies list
     */
    inner class MoviesListViewHolder(layout_view: View) : RecyclerView.ViewHolder(layout_view){

        val context: Context = layout_view.context

        // Define the views will interact with
        val movieThumbnailImageView: ImageView = layout_view.iv_movie_thumbnail
    }


    /**
     *  On Create view holder let's inflate the view holder and return it
     */
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MoviesListViewHolder {

        // Get inflater
        val inflater = LayoutInflater.from(parent?.context)

        // inflate movies_list_item.xml
        val inflated_view = inflater.inflate(R.layout.movie_list_item, parent, false)

        // return new viewHolder of the inflated view
        return MoviesListViewHolder(inflated_view)
    }


    override fun onBindViewHolder(holder: MoviesListViewHolder?, position: Int) {

       if(holder != null){

           // Get movie json object
           val movie: JSONObject = moviesList.getJSONObject(position)
           Log.d(TAG, "Movie: \n $movie")

           // set thumnail of movie
           try {

               // Get url for image thumbnail
               val thumbnail_url: String = MoviesAPI.imageUrl(img=movie.getString("poster_path"))
               Log.d(TAG, "Thumbnail url: $thumbnail_url")

               // set thumbnail
               Picasso.with(holder.context).load(thumbnail_url).into(holder.movieThumbnailImageView)


           }catch (e: Exception){
               e.printStackTrace()
           }

       }
    }

    override fun getItemCount(): Int {
        if(moviesList.length() == 0) return 0 else return moviesList.length()
    }

    /**
     *  Set new data for moviesList and notify adapter of changes
     */
    fun setMovieData(data: JSONArray){
        moviesList = data

        // notify adapter
        notifyDataSetChanged()
    }

}