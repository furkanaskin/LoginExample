package com.faskn.loginexample.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.faskn.loginexample.R
import com.faskn.loginexample.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : BaseFragment() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_feed.setOnClickListener { view ->
            navigate(R.id.action_feedFragment_to_splashFragment)
        }
        btn_signout.setOnClickListener { view ->
            firebaseAuth.signOut()
            navigate(R.id.action_feedFragment_to_splashFragment)
        }
    }

    private fun navigate(action: Int) {

        view?.let { _view ->

            Navigation.findNavController(_view).navigate(action)
        }
    }
}
