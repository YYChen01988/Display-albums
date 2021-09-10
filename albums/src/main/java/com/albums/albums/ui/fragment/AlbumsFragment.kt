package com.albums.albums.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.albums.core.ui.fragments.BaseFragment
import com.albums.databinding.FragmentAlbumsBinding

class AlbumsFragment : BaseFragment() {


    private lateinit var navController: NavController

    private lateinit var fragmentAlbumsBinding: FragmentAlbumsBinding

    override fun getErrorView(): View = fragmentAlbumsBinding.errorView.root

    override fun getErrorMessageTextView(): TextView = fragmentAlbumsBinding.errorView.tvError

    override fun getSuccessView(): View = fragmentAlbumsBinding.albumsSuccess.root

    override fun getWaitingView(): View = fragmentAlbumsBinding.waitView.root

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
        showSuccessResponse()

    }

}
