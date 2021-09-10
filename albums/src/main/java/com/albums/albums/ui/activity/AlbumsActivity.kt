package com.albums.albums.ui.activity

import android.os.Bundle
import com.albums.core.ui.activity.BaseActivity
import com.albums.databinding.ActivityAlbumsBinding

class AlbumsActivity : BaseActivity() {
    lateinit var activityAlbumsBinding: ActivityAlbumsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAlbumsBinding = ActivityAlbumsBinding.inflate(layoutInflater)
        val view = activityAlbumsBinding.root
        setContentView(view)
    }
}
