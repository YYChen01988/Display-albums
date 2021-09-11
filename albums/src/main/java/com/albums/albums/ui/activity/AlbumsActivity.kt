package com.albums.albums.ui.activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.albums.R
import com.albums.albums.data.database.SqliteHelper
import com.albums.albums.data.model.AlbumItem
import com.albums.albums.ui.adapter.AlbumsAdapter
import com.albums.albums.viewModel.AlbumsViewModel
import com.albums.core.network.Status
import com.albums.databinding.ActivityAlbumsBinding
import org.koin.android.ext.android.inject

class AlbumsActivity : AppCompatActivity() {
    private val albumsViewModel: AlbumsViewModel by inject()
    private lateinit var albumsAdapter: AlbumsAdapter
    private lateinit var sqliteHelper: SqliteHelper
    private var isAlbumOrderByTitleLength = false
    private lateinit var activityAlbumsBinding: ActivityAlbumsBinding
    fun getWaitingView(): View = activityAlbumsBinding.waitView.root


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAlbumsBinding = ActivityAlbumsBinding.inflate(layoutInflater)
        val view = activityAlbumsBinding.root
        setContentView(view)
        sqliteHelper = SqliteHelper(this)
        getAlbums()
    }


    private fun reorderAlbums(albums: List<AlbumItem>) {
        var sortedAlbums = listOf<AlbumItem>()
        activityAlbumsBinding.apply {
            this.btn.setOnClickListener {
                if (!isAlbumOrderByTitleLength) {
                    sortedAlbums = albums.sortedBy { it.title }
                    setUpAlbumsRecyclerView(sortedAlbums)
                    isAlbumOrderByTitleLength = true
                    this.btn.background =
                        resources.getDrawable(R.drawable.default_button_rounded_corner)
                } else {
                    sortedAlbums = albums.sortedBy { it.title?.length }
                    setUpAlbumsRecyclerView(sortedAlbums)
                    isAlbumOrderByTitleLength = false
                    this.btn.background =
                        resources.getDrawable(R.drawable.selected_button_rounded_corner)
                }
            }
        }
    }

    private fun getAlbums() {
        albumsViewModel.getAlbums().observe(
            this,
            {
                when (it.status) {
                    Status.LOADING -> {
                        getWaitingView()
                    }
                    Status.SUCCESS -> {
                        it.data?.let { albums ->
                            val sortedAlbums = albums.sortedBy { it.title }
                            sqliteHelper.insertAlbums(sortedAlbums)
                            setUpAlbumsFromDb()
                        }
                    }
                    Status.ERROR -> {
                        setUpAlbumsFromDb()
                        Log.d(TAG, "Error getting albums")
                    }
                }
            }
        )
        albumsViewModel.fetchAllAlbums()
    }

    private fun setUpAlbumsRecyclerView(sortedAlbums: List<AlbumItem>) {
        albumsAdapter = AlbumsAdapter(this, sortedAlbums)
        activityAlbumsBinding.rvAlbums.layoutManager = LinearLayoutManager(this)
        activityAlbumsBinding.rvAlbums.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        activityAlbumsBinding.rvAlbums.adapter = albumsAdapter
        albumsAdapter.setDataList(sortedAlbums)
    }

    private fun setUpAlbumsFromDb(){
        val albums = sqliteHelper.getAllAlbums()
        setUpAlbumsRecyclerView(albums)
        reorderAlbums(albums)

    }

}
