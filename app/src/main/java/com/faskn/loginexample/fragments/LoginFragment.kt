package com.faskn.loginexample.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.faskn.loginexample.R
import com.faskn.loginexample.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_nav_signinFragment.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
        }
    }

}