package com.arubianoch.posttest.ui

import android.os.Bundle
import com.arubianoch.posttest.R
import com.arubianoch.posttest.data.network.response.Post
import com.arubianoch.posttest.ui.adapter.PostAdapter
import com.arubianoch.posttest.ui.base.ScopedActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MainActivity : ScopedActivity(), KodeinAware, PostAdapter.OnItemClickListener {

    override val kodein by closestKodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onItemClicked(itemView: Post) {

    }
}
