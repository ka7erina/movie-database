package com.example.moviedb.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.R
import com.example.moviedb.models.Results
import com.example.moviedb.ui.detail.DetailsActivity
import com.example.moviedb.utils.INTENT_EXTRA_MOVIE_ID
import com.example.moviedb.utils.RecyclerViewDecoration
import com.example.moviedb.utils.hide
import com.example.moviedb.utils.toastMessage
import kotlinx.android.synthetic.main.content_activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainAdapter: MainAdapter
    private val vm = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMovies()

    }

    @SuppressLint("CheckResult")
    private fun getMovies() {
        vm.getMovies()
            .subscribe(
                {
                    createMovies(it.results)
                    progress_bar.hide()
                },
                this::toastMessage
            )
    }

    private fun createMovies(movieResults: ArrayList<Results>) {
        mainAdapter =
            MainAdapter(movieResults, this@MainActivity, object : MovieClickListener {
                override fun onMovieClick(movieId: Int) {
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java).apply {
                        putExtra(INTENT_EXTRA_MOVIE_ID, movieId)
                    }
                    startActivity(intent)
                }
            })
        rv_list.apply {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            addItemDecoration(RecyclerViewDecoration())
        }
    }
}