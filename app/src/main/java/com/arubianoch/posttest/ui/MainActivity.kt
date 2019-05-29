package com.arubianoch.posttest.ui

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.arubianoch.posttest.R
import com.arubianoch.posttest.data.network.response.Post
import com.arubianoch.posttest.ui.adapter.PostAdapter
import com.arubianoch.posttest.ui.base.ScopedActivity
import com.arubianoch.posttest.ui.comments.CommentFragment
import com.arubianoch.posttest.ui.post.PostViewModel
import com.arubianoch.posttest.ui.post.PostViewModelFactory
import com.arubianoch.posttest.ui.post.PostsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : ScopedActivity(), KodeinAware, PostAdapter.OnItemClickListener {

    override val kodein by closestKodein()
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private var mMenuDrawable: AnimatedVectorDrawable? = null
    private var mBackDrawable: AnimatedVectorDrawable? = null
    private var mMenuFlag: Boolean = false

    private val viewModelFactory: PostViewModelFactory by instance()
    private lateinit var viewModel: PostViewModel
    private var menuPost: Menu? = null
    private var postSelected: Int? = null

    companion object {
        const val FRAGMENTS_PAGES = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActionbar()
        setUpPagerAdapter()

        fab.setOnClickListener { onDeleteAllEntriesClick() }
    }

    private fun setUpPagerAdapter() {
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = mSectionsPagerAdapter

        tab_layout.setupWithViewPager(container)
        tab_layout.getTabAt(0)?.text = getString(R.string.tab_text_1)
        tab_layout.getTabAt(1)?.text = getString(R.string.tab_text_2)
    }

    private fun initActionbar() {
        setSupportActionBar(toolbar)

        mMenuDrawable = getDrawable(R.drawable.ic_menu_animatable) as AnimatedVectorDrawable
        mBackDrawable = getDrawable(R.drawable.ic_back_animatable) as AnimatedVectorDrawable
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_TITLE
        supportActionBar!!.setHomeAsUpIndicator(mMenuDrawable)
        mMenuDrawable?.start()
    }

    private fun onDeleteAllEntriesClick() {
        viewModel.deleteAllInfo()
    }

    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PostViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuPost = menu

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.refresh_info -> refetchPost()
            R.id.empty_star -> {
                showFullStar()
                setAsFavoritePost()
            }
            R.id.full_star -> {
                removeAsFavoritePost()
                showEmptyStar()
            }
            android.R.id.home -> {
                menuClick()
                showRefreshIcon()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun removeAsFavoritePost() {
        viewModel.setUnFavoritePost(postSelected.toString())
    }

    private fun setAsFavoritePost() {
        viewModel.setFavoritePost(postSelected.toString())
    }

    private fun refetchPost() {
        viewModel.refetchPosts()
    }

    private fun showRefreshIcon() {
        menuPost!!.findItem(R.id.full_star).isVisible = false
        menuPost!!.findItem(R.id.empty_star).isVisible = false
        menuPost!!.findItem(R.id.refresh_info).isVisible = true
    }

    private fun showEmptyStar() {
        menuPost!!.findItem(R.id.full_star).isVisible = false
        menuPost!!.findItem(R.id.empty_star).isVisible = true
        menuPost!!.findItem(R.id.refresh_info).isVisible = false
    }

    private fun showFullStar() {
        menuPost!!.findItem(R.id.full_star).isVisible = true
        menuPost!!.findItem(R.id.empty_star).isVisible = false
        menuPost!!.findItem(R.id.refresh_info).isVisible = false
    }

    private fun menuClick() {
        if (!mMenuFlag) {
            supportActionBar!!.setHomeAsUpIndicator(mMenuDrawable)
            supportActionBar!!.displayOptions = ActionBar.DISPLAY_HOME_AS_UP or ActionBar.DISPLAY_SHOW_TITLE
            mMenuDrawable?.start()
            showContainerDetail()
        } else {
            supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_TITLE
            hideContainerDetail()
        }
    }

    private fun hideContainerDetail() {
        container.visibility = View.VISIBLE
        containerDetail.visibility = View.INVISIBLE

        mMenuFlag = false
    }

    private fun showContainerDetail() {
        containerDetail.visibility = View.VISIBLE
        container.visibility = View.INVISIBLE

        supportActionBar!!.setHomeAsUpIndicator(mMenuDrawable)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_HOME_AS_UP or ActionBar.DISPLAY_SHOW_TITLE

        mMenuFlag = true
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            if (position == 0) {
                return PostsFragment.newInstance(false)
            } else {
                return PostsFragment.newInstance(true)
            }
        }

        override fun getCount(): Int {
            return FRAGMENTS_PAGES
        }
    }

    override fun onItemClicked(itemView: Post) {
        postAsReadUpdate(itemView)
        changeFavoriteIcon(itemView.isFavorite)
        setCurrentPost(itemView.id)
        openNewContainer(itemView)
        showContainerDetail()
    }

    private fun setCurrentPost(id: Int) {
        postSelected = id
    }

    private fun changeFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) showFullStar() else showEmptyStar()
    }

    private fun openNewContainer(itemView: Post) {
        this@MainActivity.supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.containerDetail,
                CommentFragment.newInstance(itemView.id.toString(), itemView.body, itemView.userId.toString())
            )
            .commit()
    }

    private fun postAsReadUpdate(itemView: Post) {
        itemView.isRead = true
        viewModel.updatePost(itemView)
    }

    override fun onBackPressed() = if (containerDetail.isVisible) hideContainerDetail() else super.onBackPressed()
}
