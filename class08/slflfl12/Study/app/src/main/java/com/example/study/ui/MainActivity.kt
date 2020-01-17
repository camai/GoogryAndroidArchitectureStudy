package com.example.study.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.study.R
import com.example.study.ui.adapter.MovieAdapter
import com.example.study.data.datasource.remote.network.NaverApiService
import com.example.study.data.model.Movie
import com.example.study.data.model.NaverSearchResponse
import com.example.study.data.repository.NaverSearchRepository
import com.example.study.data.repository.NaverSearchRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private val movieAdapter = MovieAdapter()
    private val naverSearchRepository: NaverSearchRepository = NaverSearchRepositoryImpl()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.adapter = movieAdapter

        search_button.setOnClickListener {
            getMovieList(search_editText.text.toString())
        }

        movieAdapter.setOnItemClickListener { movie ->
            var intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("movie_url", movie.link.toString())

            this.startActivity(intent)
        }
    }

    private fun getMovieList(query: String) {
        compositeDisposable.add(naverSearchRepository.getMovies(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movieAdapter.setItem(it.items)
            }, {
                it.printStackTrace()
            })
            )

    }
}




