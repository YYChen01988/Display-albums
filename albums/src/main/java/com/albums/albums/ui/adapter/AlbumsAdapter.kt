package com.albums.albums.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.albums.albums.data.model.AlbumItem
import com.albums.core.ui.activity.BaseActivity
import com.albums.databinding.ListItemAlbumBinding

class AlbumsAdapter(private val activity: BaseActivity, private val albums: List<AlbumItem>) :
    RecyclerView.Adapter<AlbumsAdapter.AlbumsAdapterViewHolder>() {

    var dataList = emptyList<AlbumItem>()

    internal fun setDataList(dataList: List<AlbumItem>) {
        this.dataList = dataList
    }

    class AlbumsAdapterViewHolder(val listItemAlbumBinding: ListItemAlbumBinding) :
        RecyclerView.ViewHolder(listItemAlbumBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsAdapterViewHolder =
        AlbumsAdapterViewHolder(
            ListItemAlbumBinding.inflate(
                LayoutInflater.from(activity),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: AlbumsAdapterViewHolder, position: Int) {
        val albumItem = albums[position]
        holder.listItemAlbumBinding.apply {
            albumTitle.text = albumItem.title

        }
    }

    override fun getItemCount(): Int = albums.count()
}