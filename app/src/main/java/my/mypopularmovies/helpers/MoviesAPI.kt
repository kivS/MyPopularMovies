package my.mypopularmovies.helpers


/**
 * Created by flying_potato on 10/03/2017.
 */
class MoviesAPI {
    companion object get{
        private val BASE_URL = "https://api.themoviedb.org/3"
        private val IMAGE_BASE_URL =  "https://image.tmdb.org/t/p"

        fun moviesNowPlayingUrl(API_KEY: String): String{
           return "$BASE_URL/movie/now_playing?api_key=$API_KEY"
        }

        fun moviesPopularUrl(API_KEY: String): String{
            return "$BASE_URL/movie/popular?api_key=$API_KEY"
        }


        fun moviesTopRatedUrl(API_KEY: String): String{
            return "$BASE_URL/movie/top_rated?api_key=$API_KEY"
        }

        fun imageUrl(width: String = "w185", img: String ): String{
            return "$IMAGE_BASE_URL/$width/$img"

        }
    }

}