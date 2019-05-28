package com.arubianoch.posttest.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arubianoch.posttest.R
import com.arubianoch.posttest.ui.MainActivity
import com.arubianoch.posttest.ui.adapter.PostAdapter
import com.arubianoch.posttest.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * @author Andres Rubiano Del Chiaro
 */
class PostsFragment : ScopedFragment(), KodeinAware {

    companion object {
        const val IS_FAVORITE = "isFavorite"

        fun newInstance(isFavoriteView: Boolean): PostsFragment {
            val postsFragment = PostsFragment()
            val args = Bundle()
            args.putBoolean(IS_FAVORITE, isFavoriteView)
            postsFragment.arguments = args
            return postsFragment
        }
    }

    override val kodein by closestKodein()
    private val viewModelFactory: PostViewModelFactory by instance()
    private var adapter: PostAdapter? = null

    private lateinit var viewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        adapter = PostAdapter(activity!!, (activity!! as MainActivity))
        retainInstance = true
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        containerPost.adapter = adapter
        containerPost.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        containerPost.addItemDecoration(dividerItemDecoration)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                @NonNull recyclerView: RecyclerView, @NonNull viewHolder: RecyclerView.ViewHolder,
                @NonNull viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(@NonNull viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val post = adapter!!.getPostAt(viewHolder.adapterPosition)
                viewModel.deletePostById(post.id.toString())
                adapter!!.notifyItemChanged(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(containerPost)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(PostViewModel::class.java)

        bindUI()
    }

    private fun bindUI() {
        val isFavoriteView = arguments!!.getBoolean("isFavorite", false)

        if (isFavoriteView) {
            fetchFavoritePost()
        } else {
            fetchNormalPost()
        }
    }

    private fun fetchNormalPost() = launch {
        val post = viewModel.posts.await()

        post.observe(activity!!, Observer {
            if (it.isNullOrEmpty()) {
                adapter!!.cleanData()
            } else {
                adapter!!.loadPosts(it)
            }
        })
    }

    private fun fetchFavoritePost() = launch {
        val post = viewModel.favoritePost.await()

        post.observe(activity!!, Observer {
            if (it.isNullOrEmpty()) {
                adapter!!.cleanData()
            } else {
                adapter!!.loadPosts(it)
            }
        })
    }

}