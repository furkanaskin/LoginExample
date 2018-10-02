package com.faskn.loginexample.fragments


import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.faskn.loginexample.R
import com.faskn.loginexample.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth


class SplashFragment : BaseFragment() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val currentUser by lazy { firebaseAuth.currentUser }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        object : CountDownTimer(3000, 1000) {
            override fun onFinish() {

                navigate(R.id.action_splashFragment_to_loginFragment)
            }

            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()
    }

    override fun onStart() {
        super.onStart()

        // Check current user available

        if(firebaseAuth.currentUser!=null){
            navigate(R.id.action_splashFragment_to_feedFragment)
            Toast.makeText(this.context,"You have successfully logged in.",Toast.LENGTH_SHORT).show()
        }else{

        }
    }

    private fun navigate(action: Int) {

        view?.let { _view ->

            Navigation.findNavController(_view).navigate(action)
        }
    }
}
