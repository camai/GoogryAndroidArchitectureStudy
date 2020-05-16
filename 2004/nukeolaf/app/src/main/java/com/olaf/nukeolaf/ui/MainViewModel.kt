package com.olaf.nukeolaf.ui

import android.os.Build
import android.text.Html
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.olaf.nukeolaf.data.model.MovieItem
import com.olaf.nukeolaf.data.model.MovieResponse
import com.olaf.nukeolaf.data.repository.MovieRepository

class MainViewModel(
    private val movieRepository: MovieRepository
) {

    val movies = ObservableField<List<MovieItem>>()

    val errorType = ObservableInt(0)

    init {
        loadMovies()
    }

    private fun loadMovies() {
        val movieList = movieRepository.getMovies()
        if (movieList != null && movieList.items.isNotEmpty()) {
            movies.set(processMovieItemString(movieList.items))
        } else {
            movies.set(listOf())
        }
    }

    fun searchMovie(query: String?) {
        if (query.isNullOrEmpty()) {
            errorType.set(EMPTY_SEARCH_WORD)
            return
        }
        movieRepository.searchMovies(
            query,
            object : MovieRepository.LoadMoviesCallback {
                override fun onMoviesLoaded(movieResponse: MovieResponse) {
                    if (movieResponse.items.isNotEmpty()) {
                        movies.set(processMovieItemString(movieResponse.items))
                    } else {
                        errorType.set(NO_QUERY_RESULT)
                    }
                }

                override fun onResponseError(message: String) {
                    errorType.set(SERVER_ERROR)
                }

                override fun onFailure(t: Throwable) {
                    errorType.set(NETWORK_ERROR)
                }
            })
    }

    private fun processMovieItemString(movies: List<MovieItem>): List<MovieItem> {
        return movies.map {
            it.copy(
                title = it.title.htmlToString(),
                subtitle = it.subtitle.htmlToString(),
                director = it.director.addCommas("감독 : "),
                actor = it.actor.addCommas("출연진 : "),
                userRating = it.userRating / 2
            )
        }
    }

    private fun String.htmlToString(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(this).toString()
        }
    }

    private fun String.addCommas(prefix: String): String {
        return if (this.isNotBlank()) {
            this.substring(0, this.length - 1)
                .split("|")
                .joinToString(
                    prefix = prefix,
                    separator = ", "
                )
        } else {
            this
        }
    }

}