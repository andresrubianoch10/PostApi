package com.arubianoch.posttest.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arubianoch.posttest.R
import com.arubianoch.posttest.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

/**
 * @author Andres Rubiano Del Chiaro
 */
class UserFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val userFactory: ((String) -> UserViewModelFactory) by factory()

    private lateinit var userViewModel: UserViewModel

    companion object {
        private const val POST_ID = "postId"

        fun newInstance(postId: String): UserFragment {
            val fragment = UserFragment()
            val args = Bundle()
            args.putString(POST_ID, postId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val postId = arguments!!.getString(POST_ID)
        userViewModel = ViewModelProviders.of(activity!!, userFactory(postId)).get(UserViewModel::class.java)
    }

    private fun bindUI() = launch {
        val users = userViewModel.user.await()

        users.observe(activity!!, Observer {
            if (it == null) {
                userEmail.text = ""
                userWebsite.text = ""
                userPhone.text = ""
                userName.text = ""
            } else {
                userEmail.text = getString(R.string.userEmailText, it.email)
                userWebsite.text = getString(R.string.userWebsiteText, it.website)
                userPhone.text = getString(R.string.userPhoneText, it.phone)
                userName.text = getString(R.string.userNameText, it.name)
            }
        })
    }
}