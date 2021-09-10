package com.albums.albums.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.albums.R
import com.albums.albums.data.model.AlbumItem
import com.albums.albums.ui.adapter.AlbumsAdapter
import com.albums.albums.viewModel.AlbumsViewModel
import com.albums.core.network.Status
import com.albums.core.ui.activity.BaseActivity
import com.albums.core.ui.fragments.BaseFragment
import com.albums.databinding.FragmentAlbumsBinding
import org.koin.android.ext.android.inject

class AlbumsFragment : BaseFragment() {
    private val albumsViewModel: AlbumsViewModel by inject()

    private lateinit var albumsAdapter: AlbumsAdapter

    private lateinit var navController: NavController

    private lateinit var fragmentAlbumsBinding: FragmentAlbumsBinding

    override fun getErrorView(): View = fragmentAlbumsBinding.errorView.root

    override fun getErrorMessageTextView(): TextView = fragmentAlbumsBinding.errorView.tvError

    override fun getSuccessView(): View = fragmentAlbumsBinding.albumsSuccess.root

    override fun getWaitingView(): View = fragmentAlbumsBinding.waitView.root

    private var isAlbumOrderByTitleLength = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentAlbumsBinding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return fragmentAlbumsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        getAlbums()

    }

    private fun reorderAlbums(albums: List<AlbumItem>) {
        var sortedAlbums = listOf<AlbumItem>()
        fragmentAlbumsBinding.albumsSuccess.apply {
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
            viewLifecycleOwner,
            {
                when (it.status) {
                    Status.LOADING -> {
                        getWaitingView()
                    }
                    Status.SUCCESS -> {
                        showSuccessResponse()
                        it.data?.let { albums ->
                            val sortedAlbums = albums.sortedBy { it.title }
                            setUpAlbumsRecyclerView(sortedAlbums)
                            reorderAlbums(albums)
                        }
                    }
                    Status.ERROR -> {
                        showError(it.message ?: getString(R.string.error_loading_data))
                    }
                }
            }
        )
        albumsViewModel.fetchAllAlbums()
    }

    private fun setUpAlbumsRecyclerView(sortedAlbums: List<AlbumItem>) {
        fragmentAlbumsBinding.albumsSuccess.rvAlbums.apply {
            albumsAdapter = AlbumsAdapter(activity as BaseActivity, sortedAlbums)
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = albumsAdapter
            albumsAdapter.setDataList(sortedAlbums)
        }
    }


}
