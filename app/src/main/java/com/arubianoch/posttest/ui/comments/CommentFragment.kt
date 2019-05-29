package com.arubianoch.posttest.ui.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.arubianoch.posttest.R
import com.arubianoch.posttest.ui.MainActivity
import com.arubianoch.posttest.ui.adapter.CommentAdapter
import com.arubianoch.posttest.ui.base.ScopedFragment
import com.arubianoch.posttest.ui.user.UserFragment
import kotlinx.android.synthetic.main.fragment_comments.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

/**
 * @author Andres Rubiano Del Chiaro
 */
class CommentFragment: ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val commentFactory: ((String) -> CommentViewModelFactory) by factory()
    private var commentAdapter: CommentAdapter? = null

    private lateinit var commentViewModel: CommentViewModel

    companion object {

        private const val POST_ID = "postId"
        private const val DESCRIPTION = "description"
        private const val USER_ID = "userId"

        fun newInstance(postId: String, description: String, userId: String) : CommentFragment {
            val fragment = CommentFragment()
            val args = Bundle()
            args.putString(POST_ID, postId)
            args.putString(DESCRIPTION, description)
            args.putString(USER_ID, userId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecyclerViewComponents()
        setDescriptionInfo()
        bindUI()
        setUserInfo()
    }

    private fun initAdapter() {
        commentAdapter = CommentAdapter(activity!!)
    }

    private fun initRecyclerViewComponents() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        commentsContainer.adapter = commentAdapter
        commentsContainer.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(),
            layoutManager.orientation)
        commentsContainer.addItemDecoration(dividerItemDecoration)
    }

    private fun setDescriptionInfo() = setDescription(arguments!!.getString(DESCRIPTION))

    private fun setUserInfo() {
        val userId = arguments!!.getString(USER_ID)

        (activity as MainActivity).supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerUser, UserFragment.newInstance(userId))
            .commit()
    }

    private fun setDescription(description: String?) {
        postDescription.text = description
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val postId = arguments?.getString(POST_ID)
        commentViewModel =
            ViewModelProviders.of(activity!!, commentFactory(postId!!)).get(CommentViewModel::class.java)
    }

    private fun bindUI() = launch {
        val comments = commentViewModel.comments.await()

        comments.observe(this@CommentFragment, Observer {
            if (it.isNullOrEmpty()) {
                commentAdapter!!.cleanData()
            } else {
                commentAdapter?.loadItems(it)
            }
        })
    }
}